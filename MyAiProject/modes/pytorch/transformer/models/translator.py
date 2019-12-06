# coding=utf-8
"""
Transformer model
"""
from torch import nn

from modes.pytorch.transformer.models.encoder_decoder import DecoderModel
from modes.pytorch.transformer.models.encoder_decoder import EncoderModel


class TranslatorModel(nn.Module):
    """
    Transformer 翻译模型
    """

    def __init__(self, num_source_vocab_size, num_target_vocab_size, max_seq_len,
                 embedding_dim=512,
                 num_encoder_decoder_layers=6,
                 num_encoder_decoder_head=8,
                 q_k_out_dim=64,
                 v_out_dim=64,
                 ff_intermediate_dim=2048,
                 use_absolute_position=False,
                 multi_attention_dropout=0.1,
                 self_attention_dropout=0.1,
                 ff_dropout=0.1):
        super(TranslatorModel, self).__init__()

        # 首先定义Encoder模型
        # encoder_output : [batch_size,batch_source_seq_len,embedding_dim]
        self.encoder = EncoderModel(num_vocab_size=num_source_vocab_size, max_seq_len=max_seq_len,
                                    embedding_dim=embedding_dim,
                                    num_encoder_layers=num_encoder_decoder_layers,
                                    num_head=num_encoder_decoder_head,
                                    q_k_out_dim=q_k_out_dim, v_out_dim=v_out_dim,
                                    ff_intermediate_dim=ff_intermediate_dim,
                                    use_absolute_position=use_absolute_position,
                                    multi_attention_dropout=multi_attention_dropout,
                                    self_attention_dropout=self_attention_dropout,
                                    ff_dropout=ff_dropout)

        # 定义Decoder模型
        # decoder_output: [batch_size,batch_target_seq_len,embedding_dim]
        self.decoder = DecoderModel(num_vocab_size=num_target_vocab_size, max_seq_len=max_seq_len,
                                    embedding_dim=embedding_dim,
                                    num_decoder_layers=num_encoder_decoder_layers,
                                    num_head=num_encoder_decoder_head,
                                    q_k_out_dim=q_k_out_dim,
                                    v_out_dim=v_out_dim,
                                    ff_intermediate_dim=ff_intermediate_dim,
                                    use_absolute_position=use_absolute_position,
                                    multi_attention_dropout=multi_attention_dropout,
                                    self_attention_dropout=self_attention_dropout,
                                    ff_dropout=ff_dropout)

        # 最后在进行全连接的输出
        # linear_output: [batch_size,batch_target_seq_len,num_target_vocab_size]
        self.linear_output = nn.Linear(embedding_dim, num_target_vocab_size, bias=False)

    def forward(self, source_seq, source_position, target_seq, target_position, return_attentions=False):
        # 因为在decode的时候是预测下一个词，所以不需用到最后一个词，要用到最后一个词的时候是在loss回归的时候(loss回归的target不需要第一个词)
        target_seq = target_seq[:, :-1]  # 将target中的最后一个词去掉
        target_position = target_position[:, :-1]  # 将最后一个词对应的position也去掉


        if return_attentions:
            encoder_output, encoder_attentions = self.encoder(source_seq, source_position,
                                                              return_attentions=return_attentions)

            decoder_output, decoder_self_attentions, decoder_encoder_attentions = self.decoder(target_seq,
                                                                                               target_position,
                                                                                               source_seq,
                                                                                               encoder_output,
                                                                                               return_attentions=return_attentions)
        else:
            encoder_output = self.encoder(source_seq, source_position, return_attentions)
            decoder_output = self.decoder(target_seq,
                                          target_position,
                                          source_seq,
                                          encoder_output,
                                          return_attentions=return_attentions)
        # 最终得到的decoder_output：[batch_size,batch_target_seq_len,embedding_dim]

        # 最终在做一次全连接
        output = self.linear_output(decoder_output)  # [batch_size,batch_target_seq_len,num_target_vocab_size]
        # 这里要做下降维操作，以方便后面进行cross_entropy的loss的计算，
        output = output.view(-1, output.size(2))  # [batch_size*batch_target_seq_len,num_target_vocab_size]

        if return_attentions:
            return output, encoder_attentions, decoder_self_attentions, decoder_encoder_attentions
        return output
