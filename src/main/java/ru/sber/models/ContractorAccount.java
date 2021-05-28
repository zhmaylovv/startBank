package ru.sber.models;

import java.math.BigDecimal;
import java.util.Objects;

public class ContractorAccount {
    private int id;
    private BigDecimal number;
    private BigDecimal balance;
    private int contractorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractorAccount that = (ContractorAccount) o;
        return contractorId == that.contractorId && number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, contractorId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getContractorId() {
        return contractorId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }
}
