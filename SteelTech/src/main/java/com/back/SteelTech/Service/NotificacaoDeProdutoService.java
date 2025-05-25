package com.back.SteelTech.Service;

import com.back.SteelTech.Entity.Notificacao;
import com.back.SteelTech.Entity.Produto;
import com.back.SteelTech.Entity.Usuario;
import com.back.SteelTech.Repository.NotificacaoRepository;
import com.back.SteelTech.Repository.UsuarioRepository;

import com.back.SteelTech.Service.ObserverProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificacaoDeProdutoService implements ObserverProduto {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void notificacaoProdutoAlterado(Produto produtoAntesAlteracao, Produto produtoDepoisAlteracao) {
        if (!Objects.equals(produtoAntesAlteracao.getPreco(), produtoDepoisAlteracao.getPreco())) {
            List<com.back.SteelTech.Entity.Usuario> listaDeUsuarios = usuarioRepository.findAll();
            for (com.back.SteelTech.Entity.Usuario usuarioAtual : listaDeUsuarios) {
                Notificacao novaNotificacao = new Notificacao();
                novaNotificacao.setUsuario(usuarioAtual);
                novaNotificacao.setProduto(produtoDepoisAlteracao);
                novaNotificacao.setMensagem(
                        produtoDepoisAlteracao.getNome() +
                                "sofreu uma alteração no valor de" + produtoAntesAlteracao.getPreco() +
                                " para" + produtoDepoisAlteracao.getPreco()
                );
                novaNotificacao.setCategoria("Atualização");
                novaNotificacao.setStatus("Pendente");

                notificacaoRepository.save(novaNotificacao);
            }
        }
    }
}
