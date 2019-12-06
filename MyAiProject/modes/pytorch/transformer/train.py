# coding=utf-8
"""transform模型任务的执行的脚本文件"""
import argparse
import torch

from modes.pytorch.optimization import AdamZZW, get_linear_schedule_with_warmup
from modes.pytorch.transformer import dataset
from modes.pytorch.transformer.dataset import TranslationDataset, train_batch_collate_fn
from tqdm import tqdm, trange  # 用于进度条展示

from modes.pytorch.transformer.models.encoder_decoder import EncoderModel, DecoderModel
from modes.pytorch.transformer.models.translator import TranslatorModel

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')


def train(model, train_data_loader, optimizer, scheduler, epochs):
    print("=====================训练开始======================")
    # 模型调整为训练模式
    model.train()
    model.zero_grad()

    epoch_iterator = trange(int(epochs), desc="Epoch")
    for _ in epoch_iterator:
        batch_iterator = tqdm(train_data_loader, desc="BatchIterating")
        for step, batch in enumerate(batch_iterator):
            batch = tuple(t.to(device) for t in batch)  # 将batch中的数据转换成对应device的数据类型
            # 取出一个batch的训练数据
            source_seq = batch[0]  # 源语言的one-hot词编码:[batch_size,seq_len]
            source_position = batch[1]  # 源语言的one-hot位置编码:[batch_size,seq_len]

            target_seq = batch[2]  # 目标语言的one-hot词编码：[batch_size,seq_len]
            target_position = batch[3]  # 目标语言的one-hot位置编码：[batch_size,seq_len]

            target_gold = target_seq[:, 1:]  # 在进行loss回归的时候不需要第一个词 [batch_size,target_seq_len]

            # 可以用 idx2word来检验数据是否正确
            # print([[source_idx2word[wordidx.item()] for wordidx in seq] for seq in
            #        source_seq])  # [['BOS', 'doozie', 'EOS', 'PAD'], ['BOS', 'foul-play', 'EOS', 'PAD'],...}
            # print([[target_idx2word[wordidx.item()] for wordidx in seq] for seq in
            #        target_seq])  # [['BOS', '出色', '的', '人', '或', '事', 'EOS'], ['BOS', '严重', '犯规', 'EOS', 'PAD', 'PAD', 'PAD'],...]

            # 前向传播:output_predict的size:[batch_size*batch_target_seq_len,num_target_vocab_size]
            output_predict = model(source_seq, source_position, target_seq, target_position)  # 输出位置上对应于

            # loss计算
            target_gold = target_gold.contiguous().view(-1)  # 打平成一维[batch_size*target_seq_len]
            loss = torch.functional.F.cross_entropy(output_predict, target_gold, ignore_index=dataset.PAD,
                                                    reduction="sum")  # sum表示所有loss的和

            # 反向传播：backward()
            loss.backward()

            # 梯度更新
            optimizer.step()
            scheduler.step()

            model.zero_grad()

            print(loss)


def main():
    # ==================================== 参数初始化 ====================================
    parser = argparse.ArgumentParser()

    # 数据源初始化
    parser.add_argument("--data_dir", default="data/train.txt", type=str, required=False,
                        help="训练数据的文件路径")  # required=True 表示是必传参数
    parser.add_argument("--output_dir", default="output", type=str, required=False,
                        help="存储模型的文件路径")

    # 模型相关的超参数初始化
    parser.add_argument("--lr", default=5e-5, type=float, help="超参数:学习速率")
    parser.add_argument("--batch_size", default=16, type=int, help="超参数:batch_size")
    parser.add_argument("--epochs", default=10, type=int, help="超参数:epochs")

    # print(args.data_dir)
    # print(args.epochs)

    args = parser.parse_args()
    embedding_dim = 512
    max_seq_len = 100
    num_encoder_decoder_layers = 2
    num_head = 2
    q_k_out_dim = 64
    v_out_dim = 64
    ff_intermediate_dim = 2048

    num_warm_up_steps = 10
    max_steps = 20
    gradient_accumulation_steps = 10
    num_train_epochs = args.epochs

    # ==================================== 数据预处理 ====================================
    train_dataset = TranslationDataset(args.data_dir)
    train_data_loader = torch.utils.data.DataLoader(train_dataset,
                                                    num_workers=2,
                                                    batch_size=args.batch_size,
                                                    collate_fn=train_batch_collate_fn,
                                                    shuffle=False)  # 我们是按照句子长度进行排序的，不能用shuffle,即使要shuffle也是在batch_collate_fn去针对batch进行shuffle

    if max_steps > 0:
        t_total = max_steps
        num_train_epochs = max_steps // (len(train_data_loader) // gradient_accumulation_steps) + 1
    else:
        t_total = len(train_data_loader) // gradient_accumulation_steps * num_train_epochs

    # ==================================== 模型初始化 ====================================
    num_source_vocab_size = train_dataset.source_vocab_size

    num_target_vocab_size = train_dataset.target_vocab_size
    transformer_model = TranslatorModel(num_source_vocab_size, num_target_vocab_size, max_seq_len,
                                        embedding_dim=embedding_dim,
                                        num_encoder_decoder_layers=num_encoder_decoder_layers,
                                        num_encoder_decoder_head=num_head,
                                        q_k_out_dim=q_k_out_dim,
                                        v_out_dim=v_out_dim,
                                        ff_intermediate_dim=ff_intermediate_dim,
                                        use_absolute_position=False,
                                        multi_attention_dropout=0.1,
                                        self_attention_dropout=0.1,
                                        ff_dropout=0.1)

    # ==================================== optimizer初始化 ====================================
    print(next(iter(transformer_model.parameters())))
    # Parameter
    # containing:
    # tensor([[0.0000, 0.0000, 0.0000, ..., 0.0000, 0.0000, 0.0000],
    #         [-0.2164, 0.6821, 0.9723, ..., -0.3191, 1.8127, -0.3311],
    #         [1.3764, -0.0156, -0.2853, ..., 0.5332, -0.0559, 1.6026],
    #         ...,
    #         [-0.6930, 0.7872, -0.1841, ..., 0.6497, -0.9351, 0.8318],
    #         [0.6115, 0.0654, -1.2383, ..., -0.5545, -1.2467, -1.3319],
    #         [-0.3409, -0.3384, -1.3304, ..., -1.0686, 1.4305, -0.7632]],
    #        requires_grad=True)
    # 过滤出为冻结的模型参数：requires_grad=True；在构建layer时freeze=False的那些层的参数：这里的话只有相对位置编码层权重不需要更新
    optimizer = AdamZZW(filter(lambda x: x.requires_grad, transformer_model.parameters()))
    scheduler = get_linear_schedule_with_warmup(optimizer, num_warmup_steps=num_warm_up_steps,
                                                num_training_steps=t_total)

    # ==================================== train开始训练 ====================================
    train(transformer_model, train_data_loader, optimizer, scheduler, num_train_epochs)


if __name__ == '__main__':
    main()
