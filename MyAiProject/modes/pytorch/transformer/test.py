# coding=utf-8
"""transform模型任务的执行的脚本文件"""
import argparse
import torch

from modes.pytorch.transformer.dataset import TranslationDataset, train_batch_collate_fn
from tqdm import tqdm

from modes.pytorch.transformer.models.encoder_decoder import EncoderModel, DecoderModel
from modes.pytorch.transformer.models.translator import TranslatorModel

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')


def train(train_data_loader):
    batch_iterator = tqdm(train_data_loader, desc="BatchIterating")
    for step, batch in enumerate(batch_iterator):
        batch = tuple(t.to(device) for t in batch)
        source_seq = batch[0]
        source_position = batch[1]

        target_seq = batch[2]
        target_position = batch[3]

        # 可以用 idx2word来检验数据是否正确
        # print([[source_idx2word[wordidx.item()] for wordidx in seq] for seq in
        #        source_seq])  # [['BOS', 'doozie', 'EOS', 'PAD'], ['BOS', 'foul-play', 'EOS', 'PAD'],...}
        # print([[target_idx2word[wordidx.item()] for wordidx in seq] for seq in
        #        target_seq])  # [['BOS', '出色', '的', '人', '或', '事', 'EOS'], ['BOS', '严重', '犯规', 'EOS', 'PAD', 'PAD', 'PAD'],...]


def main():
    # 参数初始化
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

    args = parser.parse_args()
    # print(args.data_dir)
    # print(args.epochs)

    # 数据预处理
    train_dataset = TranslationDataset(args.data_dir)
    train_data_loader = torch.utils.data.DataLoader(train_dataset,
                                                    num_workers=2,
                                                    batch_size=args.batch_size,
                                                    collate_fn=train_batch_collate_fn,
                                                    shuffle=False)  # 我们是按照句子长度进行排序的，不能用shuffle,即使要shuffle也是在batch_collate_fn去针对batch进行shuffle

    source_idx2word = train_dataset.source_vocab_idx2word
    target_idx2word = train_dataset.target_vocab_idx2word

    # 测试
    batch1 = next(iter(train_data_loader))

    embedding_dim = 512
    max_seq_len = 100
    num_encoder_decoder_layers = 2
    num_head = 2
    q_k_out_dim = 64
    v_out_dim = 64
    ff_intermediate_dim = 2048

    # EncoderModel 测试
    num_source_vocab_size = train_dataset.source_vocab_size
    source_seq = batch1[0]
    source_position = batch1[1]
    # print(source_seq)
    # print(source_position)
    encoder_model = EncoderModel(num_source_vocab_size, max_seq_len, embedding_dim, num_encoder_decoder_layers,
                                 num_head,
                                 q_k_out_dim, v_out_dim,
                                 ff_intermediate_dim,
                                 use_absolute_position=False,
                                 multi_attention_dropout=0.1,
                                 self_attention_dropout=0.1,
                                 ff_dropout=0.1)
    encoder_output = encoder_model(source_seq, source_position)
    # print(encoder_output.size())
    # lossFun = torch.nn.MSELoss()
    # loss = lossFun(encoder_output, torch.randn(encoder_output.size(0), encoder_output.size(1), encoder_output.size(2)))
    # loss.backward()

    # DecoderModel 测试
    num_target_vocab_size = train_dataset.target_vocab_size
    target_seq = batch1[2]
    target_position = batch1[3]
    decoder_model = DecoderModel(num_target_vocab_size, max_seq_len, embedding_dim, num_encoder_decoder_layers,
                                 num_head,
                                 q_k_out_dim, v_out_dim,
                                 ff_intermediate_dim,
                                 use_absolute_position=False,
                                 multi_attention_dropout=0.1,
                                 self_attention_dropout=0.1,
                                 ff_dropout=0.1)
    decoder_output = decoder_model(target_seq, target_position, source_seq, encoder_output)
    # print(decoder_output.size())
    # lossFun = torch.nn.MSELoss()
    # loss = lossFun(decoder_output, torch.randn(decoder_output.size(0), decoder_output.size(1), decoder_output.size(2)))
    # loss.backward()

    transformerModel = TranslatorModel(num_source_vocab_size, num_target_vocab_size, max_seq_len,
                                       embedding_dim=embedding_dim,
                                       num_encoder_decoder_layers=num_encoder_decoder_layers,
                                       num_encoder_decoder_head=8,
                                       q_k_out_dim=64, v_out_dim=64,
                                       ff_intermediate_dim=2048,
                                       use_absolute_position=False,
                                       multi_attention_dropout=0.1,
                                       self_attention_dropout=0.1,
                                       ff_dropout=0.1)
    output = transformerModel(source_seq, source_position, target_seq, target_position)
    print(output.size())
    lossFun = torch.nn.MSELoss()  # 采用这样的伪Loss的方式进行验证
    loss = lossFun(output, torch.randn(output.size(0), output.size(1)))
    loss.backward()


if __name__ == '__main__':
    main()
