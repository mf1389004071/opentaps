package org.opentaps.base.entities;

/*
 * Copyright (c) 2008 - 2009 Open Source Strategies, Inc.
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */

// DO NOT EDIT THIS FILE!  THIS IS AUTO GENERATED AND WILL GET WRITTEN OVER PERIODICALLY WHEN THE DATA MODEL CHANGES
// EXTEND THIS CLASS INSTEAD.

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javolution.util.FastMap;

import org.opentaps.foundation.entity.Entity;
import org.opentaps.foundation.entity.EntityFieldInterface;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;
import javax.persistence.*;
import org.hibernate.search.annotations.*;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity DataImportProduct.
 */
@javax.persistence.Entity
@Table(name="DATA_IMPORT_PRODUCT")
public class DataImportProduct extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("productId", "PRODUCT_ID");
        fields.put("productTypeId", "PRODUCT_TYPE_ID");
        fields.put("isInactive", "IS_INACTIVE");
        fields.put("customId1", "CUSTOM_ID1");
        fields.put("customId2", "CUSTOM_ID2");
        fields.put("internalName", "INTERNAL_NAME");
        fields.put("brandName", "BRAND_NAME");
        fields.put("comments", "COMMENTS");
        fields.put("productName", "PRODUCT_NAME");
        fields.put("description", "DESCRIPTION");
        fields.put("longDescription", "LONG_DESCRIPTION");
        fields.put("weight", "WEIGHT");
        fields.put("weightUomId", "WEIGHT_UOM_ID");
        fields.put("productLength", "PRODUCT_LENGTH");
        fields.put("productLengthUomId", "PRODUCT_LENGTH_UOM_ID");
        fields.put("width", "WIDTH");
        fields.put("widthUomId", "WIDTH_UOM_ID");
        fields.put("height", "HEIGHT");
        fields.put("heightUomId", "HEIGHT_UOM_ID");
        fields.put("price", "PRICE");
        fields.put("priceCurrencyUomId", "PRICE_CURRENCY_UOM_ID");
        fields.put("productFeature1", "PRODUCT_FEATURE1");
        fields.put("supplierPartyId", "SUPPLIER_PARTY_ID");
        fields.put("smallImageUrl", "SMALL_IMAGE_URL");
        fields.put("mediumImageUrl", "MEDIUM_IMAGE_URL");
        fields.put("largeImageUrl", "LARGE_IMAGE_URL");
        fields.put("purchasePrice", "PURCHASE_PRICE");
        fields.put("taxable", "TAXABLE");
        fields.put("storeId", "STORE_ID");
        fields.put("createdDate", "CREATED_DATE");
        fields.put("importStatusId", "IMPORT_STATUS_ID");
        fields.put("importError", "IMPORT_ERROR");
        fields.put("lastModifiedDate", "LAST_MODIFIED_DATE");
        fields.put("processedTimestamp", "PROCESSED_TIMESTAMP");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("DataImportProduct", fields);
}
  public static enum Fields implements EntityFieldInterface<DataImportProduct> {
    productId("productId"),
    productTypeId("productTypeId"),
    isInactive("isInactive"),
    customId1("customId1"),
    customId2("customId2"),
    internalName("internalName"),
    brandName("brandName"),
    comments("comments"),
    productName("productName"),
    description("description"),
    longDescription("longDescription"),
    weight("weight"),
    weightUomId("weightUomId"),
    productLength("productLength"),
    productLengthUomId("productLengthUomId"),
    width("width"),
    widthUomId("widthUomId"),
    height("height"),
    heightUomId("heightUomId"),
    price("price"),
    priceCurrencyUomId("priceCurrencyUomId"),
    productFeature1("productFeature1"),
    supplierPartyId("supplierPartyId"),
    smallImageUrl("smallImageUrl"),
    mediumImageUrl("mediumImageUrl"),
    largeImageUrl("largeImageUrl"),
    purchasePrice("purchasePrice"),
    taxable("taxable"),
    storeId("storeId"),
    createdDate("createdDate"),
    importStatusId("importStatusId"),
    importError("importError"),
    lastModifiedDate("lastModifiedDate"),
    processedTimestamp("processedTimestamp"),
    lastUpdatedStamp("lastUpdatedStamp"),
    lastUpdatedTxStamp("lastUpdatedTxStamp"),
    createdStamp("createdStamp"),
    createdTxStamp("createdTxStamp");
    private final String fieldName;
    private Fields(String name) { fieldName = name; }
    /** {@inheritDoc} */
    public String getName() { return fieldName; }
    /** {@inheritDoc} */
    public String asc() { return fieldName + " ASC"; }
    /** {@inheritDoc} */
    public String desc() { return fieldName + " DESC"; }
  }

   @org.hibernate.annotations.GenericGenerator(name="DataImportProduct_GEN",  strategy="org.opentaps.foundation.entity.hibernate.OpentapsIdentifierGenerator")
   @GeneratedValue(generator="DataImportProduct_GEN")
   @Id
   @Column(name="PRODUCT_ID")
   private String productId;
   @Column(name="PRODUCT_TYPE_ID")
   private String productTypeId;
   @Column(name="IS_INACTIVE")
   private String isInactive;
   @Column(name="CUSTOM_ID1")
   private String customId1;
   @Column(name="CUSTOM_ID2")
   private String customId2;
   @Column(name="INTERNAL_NAME")
   private String internalName;
   @Column(name="BRAND_NAME")
   private String brandName;
   @Column(name="COMMENTS")
   private String comments;
   @Column(name="PRODUCT_NAME")
   private String productName;
   @Column(name="DESCRIPTION")
   private String description;
   @Column(name="LONG_DESCRIPTION")
   private String longDescription;
   @Column(name="WEIGHT")
   private BigDecimal weight;
   @Column(name="WEIGHT_UOM_ID")
   private String weightUomId;
   @Column(name="PRODUCT_LENGTH")
   private BigDecimal productLength;
   @Column(name="PRODUCT_LENGTH_UOM_ID")
   private String productLengthUomId;
   @Column(name="WIDTH")
   private BigDecimal width;
   @Column(name="WIDTH_UOM_ID")
   private String widthUomId;
   @Column(name="HEIGHT")
   private BigDecimal height;
   @Column(name="HEIGHT_UOM_ID")
   private String heightUomId;
   @Column(name="PRICE")
   private BigDecimal price;
   @Column(name="PRICE_CURRENCY_UOM_ID")
   private String priceCurrencyUomId;
   @Column(name="PRODUCT_FEATURE1")
   private String productFeature1;
   @Column(name="SUPPLIER_PARTY_ID")
   private String supplierPartyId;
   @Column(name="SMALL_IMAGE_URL")
   private String smallImageUrl;
   @Column(name="MEDIUM_IMAGE_URL")
   private String mediumImageUrl;
   @Column(name="LARGE_IMAGE_URL")
   private String largeImageUrl;
   @Column(name="PURCHASE_PRICE")
   private BigDecimal purchasePrice;
   @Column(name="TAXABLE")
   private String taxable;
   @Column(name="STORE_ID")
   private String storeId;
   @Column(name="CREATED_DATE")
   private Timestamp createdDate;
   @Column(name="IMPORT_STATUS_ID")
   private String importStatusId;
   @Column(name="IMPORT_ERROR")
   private String importError;
   @Column(name="LAST_MODIFIED_DATE")
   private Timestamp lastModifiedDate;
   @Column(name="PROCESSED_TIMESTAMP")
   private Timestamp processedTimestamp;
   @Column(name="LAST_UPDATED_STAMP")
   private Timestamp lastUpdatedStamp;
   @Column(name="LAST_UPDATED_TX_STAMP")
   private Timestamp lastUpdatedTxStamp;
   @Column(name="CREATED_STAMP")
   private Timestamp createdStamp;
   @Column(name="CREATED_TX_STAMP")
   private Timestamp createdTxStamp;

  /**
   * Default constructor.
   */
  public DataImportProduct() {
      super();
      this.baseEntityName = "DataImportProduct";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("productId");
      this.allFieldsNames = new ArrayList<String>();
      this.allFieldsNames.add("productId");this.allFieldsNames.add("productTypeId");this.allFieldsNames.add("isInactive");this.allFieldsNames.add("customId1");this.allFieldsNames.add("customId2");this.allFieldsNames.add("internalName");this.allFieldsNames.add("brandName");this.allFieldsNames.add("comments");this.allFieldsNames.add("productName");this.allFieldsNames.add("description");this.allFieldsNames.add("longDescription");this.allFieldsNames.add("weight");this.allFieldsNames.add("weightUomId");this.allFieldsNames.add("productLength");this.allFieldsNames.add("productLengthUomId");this.allFieldsNames.add("width");this.allFieldsNames.add("widthUomId");this.allFieldsNames.add("height");this.allFieldsNames.add("heightUomId");this.allFieldsNames.add("price");this.allFieldsNames.add("priceCurrencyUomId");this.allFieldsNames.add("productFeature1");this.allFieldsNames.add("supplierPartyId");this.allFieldsNames.add("smallImageUrl");this.allFieldsNames.add("mediumImageUrl");this.allFieldsNames.add("largeImageUrl");this.allFieldsNames.add("purchasePrice");this.allFieldsNames.add("taxable");this.allFieldsNames.add("storeId");this.allFieldsNames.add("createdDate");this.allFieldsNames.add("importStatusId");this.allFieldsNames.add("importError");this.allFieldsNames.add("lastModifiedDate");this.allFieldsNames.add("processedTimestamp");this.allFieldsNames.add("lastUpdatedStamp");this.allFieldsNames.add("lastUpdatedTxStamp");this.allFieldsNames.add("createdStamp");this.allFieldsNames.add("createdTxStamp");
      this.nonPrimaryKeyNames = new ArrayList<String>();
      this.nonPrimaryKeyNames.addAll(allFieldsNames);
      this.nonPrimaryKeyNames.removeAll(primaryKeyNames);
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public DataImportProduct(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * Auto generated value setter.
     * @param productTypeId the productTypeId to set
     */
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }
    /**
     * Auto generated value setter.
     * @param isInactive the isInactive to set
     */
    public void setIsInactive(String isInactive) {
        this.isInactive = isInactive;
    }
    /**
     * Auto generated value setter.
     * @param customId1 the customId1 to set
     */
    public void setCustomId1(String customId1) {
        this.customId1 = customId1;
    }
    /**
     * Auto generated value setter.
     * @param customId2 the customId2 to set
     */
    public void setCustomId2(String customId2) {
        this.customId2 = customId2;
    }
    /**
     * Auto generated value setter.
     * @param internalName the internalName to set
     */
    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }
    /**
     * Auto generated value setter.
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    /**
     * Auto generated value setter.
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * Auto generated value setter.
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * Auto generated value setter.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Auto generated value setter.
     * @param longDescription the longDescription to set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    /**
     * Auto generated value setter.
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    /**
     * Auto generated value setter.
     * @param weightUomId the weightUomId to set
     */
    public void setWeightUomId(String weightUomId) {
        this.weightUomId = weightUomId;
    }
    /**
     * Auto generated value setter.
     * @param productLength the productLength to set
     */
    public void setProductLength(BigDecimal productLength) {
        this.productLength = productLength;
    }
    /**
     * Auto generated value setter.
     * @param productLengthUomId the productLengthUomId to set
     */
    public void setProductLengthUomId(String productLengthUomId) {
        this.productLengthUomId = productLengthUomId;
    }
    /**
     * Auto generated value setter.
     * @param width the width to set
     */
    public void setWidth(BigDecimal width) {
        this.width = width;
    }
    /**
     * Auto generated value setter.
     * @param widthUomId the widthUomId to set
     */
    public void setWidthUomId(String widthUomId) {
        this.widthUomId = widthUomId;
    }
    /**
     * Auto generated value setter.
     * @param height the height to set
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }
    /**
     * Auto generated value setter.
     * @param heightUomId the heightUomId to set
     */
    public void setHeightUomId(String heightUomId) {
        this.heightUomId = heightUomId;
    }
    /**
     * Auto generated value setter.
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * Auto generated value setter.
     * @param priceCurrencyUomId the priceCurrencyUomId to set
     */
    public void setPriceCurrencyUomId(String priceCurrencyUomId) {
        this.priceCurrencyUomId = priceCurrencyUomId;
    }
    /**
     * Auto generated value setter.
     * @param productFeature1 the productFeature1 to set
     */
    public void setProductFeature1(String productFeature1) {
        this.productFeature1 = productFeature1;
    }
    /**
     * Auto generated value setter.
     * @param supplierPartyId the supplierPartyId to set
     */
    public void setSupplierPartyId(String supplierPartyId) {
        this.supplierPartyId = supplierPartyId;
    }
    /**
     * Auto generated value setter.
     * @param smallImageUrl the smallImageUrl to set
     */
    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }
    /**
     * Auto generated value setter.
     * @param mediumImageUrl the mediumImageUrl to set
     */
    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }
    /**
     * Auto generated value setter.
     * @param largeImageUrl the largeImageUrl to set
     */
    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }
    /**
     * Auto generated value setter.
     * @param purchasePrice the purchasePrice to set
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    /**
     * Auto generated value setter.
     * @param taxable the taxable to set
     */
    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }
    /**
     * Auto generated value setter.
     * @param storeId the storeId to set
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    /**
     * Auto generated value setter.
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * Auto generated value setter.
     * @param importStatusId the importStatusId to set
     */
    public void setImportStatusId(String importStatusId) {
        this.importStatusId = importStatusId;
    }
    /**
     * Auto generated value setter.
     * @param importError the importError to set
     */
    public void setImportError(String importError) {
        this.importError = importError;
    }
    /**
     * Auto generated value setter.
     * @param lastModifiedDate the lastModifiedDate to set
     */
    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    /**
     * Auto generated value setter.
     * @param processedTimestamp the processedTimestamp to set
     */
    public void setProcessedTimestamp(Timestamp processedTimestamp) {
        this.processedTimestamp = processedTimestamp;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedStamp the lastUpdatedStamp to set
     */
    public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedTxStamp the lastUpdatedTxStamp to set
     */
    public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdStamp the createdStamp to set
     */
    public void setCreatedStamp(Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdTxStamp the createdTxStamp to set
     */
    public void setCreatedTxStamp(Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductId() {
        return this.productId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductTypeId() {
        return this.productTypeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getIsInactive() {
        return this.isInactive;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomId1() {
        return this.customId1;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getCustomId2() {
        return this.customId2;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getInternalName() {
        return this.internalName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getBrandName() {
        return this.brandName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getComments() {
        return this.comments;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductName() {
        return this.productName;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getLongDescription() {
        return this.longDescription;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getWeight() {
        return this.weight;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWeightUomId() {
        return this.weightUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getProductLength() {
        return this.productLength;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductLengthUomId() {
        return this.productLengthUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getWidth() {
        return this.width;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getWidthUomId() {
        return this.widthUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getHeight() {
        return this.height;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getHeightUomId() {
        return this.heightUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getPrice() {
        return this.price;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getPriceCurrencyUomId() {
        return this.priceCurrencyUomId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductFeature1() {
        return this.productFeature1;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getSupplierPartyId() {
        return this.supplierPartyId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getSmallImageUrl() {
        return this.smallImageUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getMediumImageUrl() {
        return this.mediumImageUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getLargeImageUrl() {
        return this.largeImageUrl;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getPurchasePrice() {
        return this.purchasePrice;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getTaxable() {
        return this.taxable;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getStoreId() {
        return this.storeId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedDate() {
        return this.createdDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getImportStatusId() {
        return this.importStatusId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getImportError() {
        return this.importError;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getProcessedTimestamp() {
        return this.processedTimestamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedStamp() {
        return this.lastUpdatedStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedTxStamp() {
        return this.lastUpdatedTxStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedStamp() {
        return this.createdStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedTxStamp() {
        return this.createdTxStamp;
    }




    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setProductId((String) mapValue.get("productId"));
        setProductTypeId((String) mapValue.get("productTypeId"));
        setIsInactive((String) mapValue.get("isInactive"));
        setCustomId1((String) mapValue.get("customId1"));
        setCustomId2((String) mapValue.get("customId2"));
        setInternalName((String) mapValue.get("internalName"));
        setBrandName((String) mapValue.get("brandName"));
        setComments((String) mapValue.get("comments"));
        setProductName((String) mapValue.get("productName"));
        setDescription((String) mapValue.get("description"));
        setLongDescription((String) mapValue.get("longDescription"));
        setWeight(convertToBigDecimal(mapValue.get("weight")));
        setWeightUomId((String) mapValue.get("weightUomId"));
        setProductLength(convertToBigDecimal(mapValue.get("productLength")));
        setProductLengthUomId((String) mapValue.get("productLengthUomId"));
        setWidth(convertToBigDecimal(mapValue.get("width")));
        setWidthUomId((String) mapValue.get("widthUomId"));
        setHeight(convertToBigDecimal(mapValue.get("height")));
        setHeightUomId((String) mapValue.get("heightUomId"));
        setPrice(convertToBigDecimal(mapValue.get("price")));
        setPriceCurrencyUomId((String) mapValue.get("priceCurrencyUomId"));
        setProductFeature1((String) mapValue.get("productFeature1"));
        setSupplierPartyId((String) mapValue.get("supplierPartyId"));
        setSmallImageUrl((String) mapValue.get("smallImageUrl"));
        setMediumImageUrl((String) mapValue.get("mediumImageUrl"));
        setLargeImageUrl((String) mapValue.get("largeImageUrl"));
        setPurchasePrice(convertToBigDecimal(mapValue.get("purchasePrice")));
        setTaxable((String) mapValue.get("taxable"));
        setStoreId((String) mapValue.get("storeId"));
        setCreatedDate((Timestamp) mapValue.get("createdDate"));
        setImportStatusId((String) mapValue.get("importStatusId"));
        setImportError((String) mapValue.get("importError"));
        setLastModifiedDate((Timestamp) mapValue.get("lastModifiedDate"));
        setProcessedTimestamp((Timestamp) mapValue.get("processedTimestamp"));
        setLastUpdatedStamp((Timestamp) mapValue.get("lastUpdatedStamp"));
        setLastUpdatedTxStamp((Timestamp) mapValue.get("lastUpdatedTxStamp"));
        setCreatedStamp((Timestamp) mapValue.get("createdStamp"));
        setCreatedTxStamp((Timestamp) mapValue.get("createdTxStamp"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("productId", getProductId());
        mapValue.put("productTypeId", getProductTypeId());
        mapValue.put("isInactive", getIsInactive());
        mapValue.put("customId1", getCustomId1());
        mapValue.put("customId2", getCustomId2());
        mapValue.put("internalName", getInternalName());
        mapValue.put("brandName", getBrandName());
        mapValue.put("comments", getComments());
        mapValue.put("productName", getProductName());
        mapValue.put("description", getDescription());
        mapValue.put("longDescription", getLongDescription());
        mapValue.put("weight", getWeight());
        mapValue.put("weightUomId", getWeightUomId());
        mapValue.put("productLength", getProductLength());
        mapValue.put("productLengthUomId", getProductLengthUomId());
        mapValue.put("width", getWidth());
        mapValue.put("widthUomId", getWidthUomId());
        mapValue.put("height", getHeight());
        mapValue.put("heightUomId", getHeightUomId());
        mapValue.put("price", getPrice());
        mapValue.put("priceCurrencyUomId", getPriceCurrencyUomId());
        mapValue.put("productFeature1", getProductFeature1());
        mapValue.put("supplierPartyId", getSupplierPartyId());
        mapValue.put("smallImageUrl", getSmallImageUrl());
        mapValue.put("mediumImageUrl", getMediumImageUrl());
        mapValue.put("largeImageUrl", getLargeImageUrl());
        mapValue.put("purchasePrice", getPurchasePrice());
        mapValue.put("taxable", getTaxable());
        mapValue.put("storeId", getStoreId());
        mapValue.put("createdDate", getCreatedDate());
        mapValue.put("importStatusId", getImportStatusId());
        mapValue.put("importError", getImportError());
        mapValue.put("lastModifiedDate", getLastModifiedDate());
        mapValue.put("processedTimestamp", getProcessedTimestamp());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}
