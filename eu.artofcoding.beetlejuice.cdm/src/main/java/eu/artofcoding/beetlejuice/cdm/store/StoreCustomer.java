/*
 * beetlejuice
 * beetlejuice-cdm
 * Copyright (C) 2011-2013 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 13.02.13 12:14
 */

package eu.artofcoding.beetlejuice.cdm.store;

import eu.artofcoding.beetlejuice.cdm.Base;
import eu.artofcoding.beetlejuice.cdm.Salutation;
import eu.artofcoding.beetlejuice.cdm.accounting.BankAccount;
import eu.artofcoding.beetlejuice.cdm.accounting.Invoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StoreCustomer of a store.
 */
public class StoreCustomer extends Base {

    private StoreIdent storeIdent;

    private String agentIdent;

    private String customerIdent;

    private Salutation salutation;

    private String firstname;

    private String lastname;

    private String fullname;

    private String shippingAddress1;

    private String shippingAddress1StreetNumber;

    private String shippingAddress2;

    private String zipcode;

    private String city;

    private String phone;

    private String email;

    private BankAccount bankAccount = new BankAccount();

    /**
     * Login (via external service) ok?
     */
    private boolean loginOk;

    /**
     * Why did the login fail?
     */
    private String loginFailedReason;

    /**
     * Map invoice number -> invoice data.
     */
    private Map<String, Invoice> invoices = new HashMap<>();

    //<editor-fold desc="Getter and Setter">

    public StoreIdent getStoreIdent() {
        return storeIdent;
    }

    public void setStoreIdent(StoreIdent storeIdent) {
        this.storeIdent = storeIdent;
    }

    public String getAgentIdent() {
        return agentIdent;
    }

    public void setAgentIdent(String agentIdent) {
        this.agentIdent = agentIdent;
    }

    public String getCustomerIdent() {
        return customerIdent;
    }

    public void setCustomerIdent(String customerIdent) {
        this.customerIdent = customerIdent;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        String _fn;
        if (null == fullname) {
            // Combine salutation, first name and last name.
            // HERR -> Herr
            Salutation salutation = getSalutation();
            if (null != salutation) {
                String salutation1 = salutation.toString().substring(0, 1);
                String salutation2 = salutation.toString().substring(1).toLowerCase();
                _fn = String.format("%s%s %s %s", salutation1, salutation2, getFirstname(), getLastname());
            } else {
                _fn = String.format("%s %s", getFirstname(), getLastname());
            }
        } else {
            _fn = fullname;
        }
        return _fn;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress1StreetNumber() {
        return shippingAddress1StreetNumber;
    }

    public void setShippingAddress1StreetNumber(String shippingAddress1StreetNumber) {
        this.shippingAddress1StreetNumber = shippingAddress1StreetNumber;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Map<String, Invoice> invoices) {
        this.invoices = invoices;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean isLoginOk() {
        return loginOk;
    }

    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }

    public String getLoginFailedReason() {
        return loginFailedReason;
    }

    public void setLoginFailedReason(String loginFailedReason) {
        this.loginFailedReason = loginFailedReason;
    }

    //</editor-fold>

    /**
     * Add an invoice.
     * @param invoiceIdent Identification of invoice.
     */
    public void addInvoice(String invoiceIdent, Invoice invoice) {
        invoices.put(invoiceIdent, invoice);
    }

    /**
     * Remove an invoice by its ident.
     * @param invoiceIdent Identification of invoice.
     */
    public void removeInvoice(String invoiceIdent) {
        invoices.remove(invoiceIdent);
    }

    /**
     * Remove an invoice by comparing objects.
     * @param invoice Instance of {@link Invoice}.
     */
    public void removeInvoice(Invoice invoice) {
        for (String invoiceIdent : invoices.keySet()) {
            if (invoiceIdent.equals(invoice.getInvoiceIdent())) {
                invoices.remove(invoiceIdent);
            }
        }
    }

    /**
     * Get an invoice by its ident.
     * @param invoiceIdent Identification of invoice.
     * @return {@link Invoice}
     */
    public Invoice getInvoice(String invoiceIdent) {
        return invoices.get(invoiceIdent);
    }

    /**
     * Get all articles which should be returned.
     * @return List&lt;Article> with all to-be-returned articles.
     */
    public List<Article> getReturnArticles() {
        List<Article> articles = new ArrayList<>(10);
        // All invoices loaded for customer
        Map<String, Invoice> invoices = getInvoices();
        for (String invoiceIdent : invoices.keySet()) {
            articles.addAll(invoices.get(invoiceIdent).getReturnArticles());
        }
        return articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreCustomer)) return false;
        StoreCustomer storeCustomer = (StoreCustomer) o;
        if (city != null ? !city.equals(storeCustomer.city) : storeCustomer.city != null) return false;
        if (!customerIdent.equals(storeCustomer.customerIdent)) return false;
        if (shippingAddress1 != null ? !shippingAddress1.equals(storeCustomer.shippingAddress1) : storeCustomer.shippingAddress1 != null)
            return false;
        if (shippingAddress2 != null ? !shippingAddress2.equals(storeCustomer.shippingAddress2) : storeCustomer.shippingAddress2 != null)
            return false;
        if (zipcode != null ? !zipcode.equals(storeCustomer.zipcode) : storeCustomer.zipcode != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = customerIdent.hashCode();
        result = 31 * result + (shippingAddress1 != null ? shippingAddress1.hashCode() : 0);
        result = 31 * result + (shippingAddress2 != null ? shippingAddress2.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

}
