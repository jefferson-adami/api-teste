package br.com.jca.Enum;

import java.util.Arrays;
import java.util.Optional;

public enum ProdutoEnum {

    CPF(1),
    CNPJ(2),
    EMAIL(3),
    TELEFONE(4),
    EVP(5),
    OUTROS(99);

    private int codigo;

    ProdutoEnum(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Optional<ProdutoEnum> fromCodigo(Integer codigo) {
      return Optional.of(Arrays.stream(ProdutoEnum.values()).filter(p ->
              p.codigo == codigo).findAny().orElse(ProdutoEnum.OUTROS));
    }
}
