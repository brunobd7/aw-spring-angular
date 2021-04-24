package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**ANOTATION PARA DEFINIR NOME DA CONFIG*/
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private String origemPermitida = "http://localhost:8000";

    private final Seguranca seguranca = new Seguranca();


    public Seguranca getSeguranca() {
        return seguranca;
    }

    public String getOrigemPermitida() {
        return origemPermitida;
    }

    public void setOrigemPermitida(String origemPermitida) {
        this.origemPermitida = origemPermitida;
    }

    //separando por tipo de configuracao "Seguranca" , "Infra" , "DevOps" POR EXEMPLO
    public class Seguranca{

        private boolean enableHttps;


        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }
}
