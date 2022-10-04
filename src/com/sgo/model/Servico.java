/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author felip
 */
@Entity
@Table(name = "servico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servico.findAll", query = "SELECT s FROM Servico s"),
    @NamedQuery(name = "Servico.findByCodigo", query = "SELECT s FROM Servico s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Servico.findByTipoServico", query = "SELECT s FROM Servico s WHERE s.tipoServico = :tipoServico"),
    @NamedQuery(name = "Servico.findByStatusObra", query = "SELECT s FROM Servico s WHERE s.statusObra = :statusObra"),
    @NamedQuery(name = "Servico.findByRua", query = "SELECT s FROM Servico s WHERE s.rua = :rua"),
    @NamedQuery(name = "Servico.findByBairro", query = "SELECT s FROM Servico s WHERE s.bairro = :bairro"),
    @NamedQuery(name = "Servico.findByNumero", query = "SELECT s FROM Servico s WHERE s.numero = :numero"),
    @NamedQuery(name = "Servico.findByCidade", query = "SELECT s FROM Servico s WHERE s.cidade = :cidade"),
    @NamedQuery(name = "Servico.findByDescricao", query = "SELECT s FROM Servico s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "Servico.findByPreco", query = "SELECT s FROM Servico s WHERE s.preco = :preco"),
    @NamedQuery(name = "Servico.findByDataObra", query = "SELECT s FROM Servico s WHERE s.dataObra = :dataObra"),
    @NamedQuery(name = "Servico.findByCodcliente", query = "SELECT s FROM Servico s WHERE s.codcliente = :codcliente"),
    @NamedQuery(name = "Servico.findByDataTermino", query = "SELECT s FROM Servico s WHERE s.dataTermino = :dataTermino")})
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "tipoServico")
    private String tipoServico;
    @Column(name = "statusObra")
    @Enumerated(EnumType.STRING)
    private StatusObra statusObra;
    @Column(name = "rua")
    private String rua;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "preco")
    private String preco;
    @Column(name = "dataObra")
    private String dataObra;
    @Column(name = "codcliente")
    private String codcliente;
    @Column(name = "dataTermino")
    private String dataTermino;

    public Servico() {
    }

    public Servico(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public StatusObra getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(StatusObra statusObra) {
        this.statusObra = statusObra;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDataObra() {
        return dataObra;
    }

    public void setDataObra(String dataObra) {
        this.dataObra = dataObra;
    }

    public String getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(String codcliente) {
        this.codcliente = codcliente;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servico)) {
            return false;
        }
        Servico other = (Servico) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sgo.model.Servico[ codigo=" + codigo + " ]";
    }
    
}
