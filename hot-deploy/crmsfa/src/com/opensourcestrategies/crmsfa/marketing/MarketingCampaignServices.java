/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Honest Public License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Honest Public License for more details.
 *
 * You should have received a copy of the Honest Public License
 * along with this program; if not, write to Funambol,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */

package com.opensourcestrategies.crmsfa.marketing;

import com.opensourcestrategies.crmsfa.party.PartyHelper;
import com.opensourcestrategies.crmsfa.security.CrmsfaSecurity;

import org.opentaps.common.util.UtilCommon;
import org.opentaps.common.util.UtilMessage;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.security.Security;
import org.ofbiz.service.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MarketingCampaign services. The service documentation is in services_marketing.xml.
 *
 * @author     <a href="mailto:leon@opensourcestrategies.com">Leon Torres</a>
 * @author     <a href="mailto:florin.patrascu@gmail.com">Florin T.PATRASCU</a>
 */
public class MarketingCampaignServices {

    public static final String module = MarketingCampaignServices.class.getName();
    public static final String errorResource = "CRMSFAErrorLabels";
    public static final String voipResource = "VoIP";

    public static Map addAccountMarketingCampaign(DispatchContext dctx, Map context) {
        return addMarketingCampaignWithPermission(dctx, context, "CRMSFA_ACCOUNT", "_UPDATE", "ACCOUNT");
    }

    public static Map addContactMarketingCampaign(DispatchContext dctx, Map context) {
        return addMarketingCampaignWithPermission(dctx, context, "CRMSFA_CONTACT", "_UPDATE", "CONTACT");
    }

    public static Map addLeadMarketingCampaign(DispatchContext dctx, Map context) {
        return addMarketingCampaignWithPermission(dctx, context, "CRMSFA_LEAD", "_UPDATE", "PROSPECT");
    }

    /**
     * Parametrized service to add a marketing campaign to a party. Pass in the security to check.
     */
    private static Map addMarketingCampaignWithPermission(DispatchContext dctx, Map context, String module, String operation, String roleTypeId) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Security security = dctx.getSecurity();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");

        String partyId = (String) context.get("partyId");
        String marketingCampaignId = (String) context.get("marketingCampaignId");

        // check parametrized security
        if (!CrmsfaSecurity.hasPartyRelationSecurity(security, module, operation, userLogin, partyId)) {
            return UtilMessage.createAndLogServiceError("CrmErrorPermissionDenied", locale, module);
        }
        try {
            // create the MarketingCampaignRole to relate the optional marketing campaign to this party as the system user
            GenericValue system = delegator.findByPrimaryKeyCache("UserLogin", UtilMisc.toMap("userLoginId", "system"));
            Map serviceResults = dispatcher.runSync("createMarketingCampaignRole", 
                    UtilMisc.toMap("partyId", partyId , "roleTypeId", roleTypeId, "marketingCampaignId", marketingCampaignId, "userLogin", system));
            if (ServiceUtil.isError(serviceResults)) {
                return UtilMessage.createAndLogServiceError(serviceResults, "CrmErrorAddMarketingCampaign", locale, module);
            }
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorAddMarketingCampaign", locale, module);
        } catch (GenericServiceException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorAddMarketingCampaign", locale, module);
        }
        return ServiceUtil.returnSuccess();
    }

    public static Map removeAccountMarketingCampaign(DispatchContext dctx, Map context) {
        return removeMarketingCampaignWithPermission(dctx, context, "CRMSFA_ACCOUNT", "_UPDATE", "ACCOUNT");
    }

    public static Map removeContactMarketingCampaign(DispatchContext dctx, Map context) {
        return removeMarketingCampaignWithPermission(dctx, context, "CRMSFA_CONTACT", "_UPDATE", "CONTACT");
    }

    public static Map removeLeadMarketingCampaign(DispatchContext dctx, Map context) {
        return removeMarketingCampaignWithPermission(dctx, context, "CRMSFA_LEAD", "_UPDATE", "PROSPECT");
    }

    /**
     * Parametrized method to remove a marketing campaign from a party. Pass in the security to check.
     */
    private static Map removeMarketingCampaignWithPermission(DispatchContext dctx, Map context, String module, String operation, String roleTypeId) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Security security = dctx.getSecurity();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");

        String partyId = (String) context.get("partyId");
        String marketingCampaignId = (String) context.get("marketingCampaignId");

        // check parametrized security
        if (!CrmsfaSecurity.hasPartyRelationSecurity(security, module, operation, userLogin, partyId)) {
            return UtilMessage.createAndLogServiceError("CrmErrorPermissionDenied", locale, module);
        }
        try {
            // just remove the MarketingCampaignRole as the system user
            GenericValue system = delegator.findByPrimaryKeyCache("UserLogin", UtilMisc.toMap("userLoginId", "system"));
            Map serviceResults = dispatcher.runSync("deleteMarketingCampaignRole",
                    UtilMisc.toMap("partyId", partyId, "marketingCampaignId", marketingCampaignId, "roleTypeId", roleTypeId, "userLogin", system));
            if (ServiceUtil.isError(serviceResults)) {
                return UtilMessage.createAndLogServiceError(serviceResults, "CrmErrorRemoveMarketingCampaign", locale, module);
            }
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorRemoveMarketingCampaign", locale, module);
        } catch (GenericServiceException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorRemoveMarketingCampaign", locale, module);
        }

        return ServiceUtil.returnSuccess();
    }

    public static Map sendMarketingCampaignEmail(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");
        String marketingCampaignId = (String) context.get("marketingCampaignId");

        // TODO: security
        try {
            // create a communicaiton event for each contact list and invoke sendCommEventAsEmail
            List contactLists = delegator.findByAnd("ContactList", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
            contactLists = EntityUtil.filterByAnd(contactLists, UtilMisc.toMap("contactMechTypeId", "EMAIL_ADDRESS"));
            for (Iterator iter = contactLists.iterator(); iter.hasNext(); ) {
                GenericValue contactList = (GenericValue) iter.next();

                ModelService service = dctx.getModelService("createCommunicationEvent");
                Map input = service.makeValid(context, "IN");
                input.put("contactListId", contactList.get("contactListId"));
                input.put("entryDate", UtilDateTime.nowTimestamp());
                input.put("communicationEventTypeId", "EMAIL_COMMUNICATION");
                input.put("partyIdFrom", userLogin.getString("partyId"));
                input.put("roleTypeIdFrom", PartyHelper.getFirstValidRoleTypeId(userLogin.getString("partyId"), PartyHelper.TEAM_MEMBER_ROLES, delegator));
                Map serviceResults = dispatcher.runSync("createCommunicationEvent", input);
                if (ServiceUtil.isError(serviceResults)) {
                    return UtilMessage.createAndLogServiceError(serviceResults, "CrmErrorSendEmailToMarketingCampaignFail", locale, module);
                }
                String communicationEventId = (String) serviceResults.get("communicationEventId");

                serviceResults = dispatcher.runSync("sendCommEventAsEmail", UtilMisc.toMap("communicationEventId", communicationEventId, "userLogin", userLogin));
                if (ServiceUtil.isError(serviceResults)) {
                    return UtilMessage.createAndLogServiceError(serviceResults, "CrmErrorSendEmailToMarketingCampaignFail", locale, module);
                }
            }
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorSendEmailToMarketingCampaignFail", locale, module);
        } catch (GenericServiceException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorSendEmailToMarketingCampaignFail", locale, module);
        }

        return ServiceUtil.returnSuccess();
    }

    public static Map addNewCatalogRequestsToContactList(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");

        String contactListId = (String) context.get("contactListId");
        String includeCountryGeoId = (String) context.get("includeCountryGeoId");
        String excludeCountryGeoId = (String) context.get("excludeCountryGeoId");

        try {
            // first make sure the contact list is a postal address type
            GenericValue contactList = delegator.findByPrimaryKeyCache("ContactList", UtilMisc.toMap("contactListId", contactListId));
            if (contactList == null) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotFound", UtilMisc.toMap("contactListId", contactListId), locale, module);
            }
            if (!"POSTAL_ADDRESS".equals(contactList.get("contactMechTypeId"))) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotAddress", contactList.getAllFields(), locale, module);
            }

            // get all the submitted requests of type RF_CATALOG, filtered for country
            List conditions = UtilMisc.toList(
                    new EntityExpr("custRequestTypeId", EntityOperator.EQUALS, "RF_CATALOG"),
                    new EntityExpr("statusId", EntityOperator.EQUALS, "CRQ_SUBMITTED")
                    );
            if (includeCountryGeoId != null && includeCountryGeoId.trim().length() > 0) {
                conditions.add( new EntityExpr("countryGeoId", EntityOperator.EQUALS, includeCountryGeoId.trim()) );
            }
            if (excludeCountryGeoId != null && excludeCountryGeoId.trim().length() > 0) {
                conditions.add( new EntityExpr("countryGeoId", EntityOperator.NOT_EQUAL, excludeCountryGeoId.trim()) );
            }
            List requests = delegator.findByCondition("CustRequestAndAddress", new EntityConditionList(conditions, EntityOperator.AND), null, null);

            for (Iterator iter = requests.iterator(); iter.hasNext(); ) {
                GenericValue request = (GenericValue) iter.next();

                // create a new contact list party by hand
                Map input = UtilMisc.toMap("contactListId", contactListId, "partyId", request.get("fromPartyId"));
                input.put("preferredContactMechId", request.get("contactMechId"));
                input.put("statusId", "CLPT_ACCEPTED");
                input.put("fromDate", UtilDateTime.nowTimestamp());
                GenericValue contactListParty = delegator.makeValue("ContactListParty", input);
                contactListParty.create();

                // update the CustRequest to CRQ_ACCEPTED
                GenericValue custRequest = request.getRelatedOne("CustRequest");
                custRequest.set("statusId", "CRQ_ACCEPTED");
                custRequest.store();
            }
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorAddNewCatalogRequests", locale, module);
        }

        return ServiceUtil.returnSuccess();
    }

    public static Map sortUSPSBusinessMail(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");
        String contactListId = (String) context.get("contactListId");
        Timestamp now = UtilDateTime.nowTimestamp();

        try {
            // verify that the contact list is POSTAL_ADDRESS 
            GenericValue contactList = delegator.findByPrimaryKey("ContactList", UtilMisc.toMap("contactListId", contactListId));
            if (contactList == null) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotFound", UtilMisc.toMap("contactListId", contactListId), locale, module);
            }
            if (!"POSTAL_ADDRESS".equals(contactList.get("contactMechTypeId"))) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotAddress", contactList.getAllFields(), locale, module);
            }

            /* and has at least the required 300 USA postal addresses
            long members = MarketingHelper.countContactListMembersByCountry(contactListId, "USA", delegator);
            if (members < 300) {
                return UtilMessage.createAndLogServiceError("CrmErrorUSPSNotEnoughMembers", contactList.getAllFields(), locale, module);
            }
            */

            // obtain a map of zip3 -> bmcCode to use as override values
            Map overrideBMC = MarketingHelper.getZipToBMCCode(delegator);

            // clear out the existing sorted list
            delegator.removeByAnd("UspsContactListSort", UtilMisc.toMap("contactListId", contactListId));

            // commonly used conditions
            EntityCondition contactListCond = new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId);
            EntityCondition notProcessedCond = new EntityExpr("processedTimestamp", EntityOperator.EQUALS, null);

            // get the unexpired, appepted, distinct ContactListPartyAndAddress from the USA
            List orderBy = UtilMisc.toList("postalCode");
            EntityCondition conditions = new EntityConditionList( UtilMisc.toList(
                        contactListCond,
                        EntityUtil.getFilterByDateExpr(),
                        new EntityExpr("statusId", EntityOperator.EQUALS, "CLPT_ACCEPTED"),
                        new EntityExpr("countryGeoId", EntityOperator.EQUALS, "USA")
                        ), EntityOperator.AND);
            EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
            EntityListIterator iterator = delegator.findListIteratorByCondition("ContactListPartyAndAddress", conditions, null, null, orderBy, options);

            // for each address, create a UspsContactListSort with the BMC it belongs to, if any
            GenericValue address = null;
            while ((address = (GenericValue) iterator.next()) != null) {
                String zip5 = address.getString("postalCode");
                if (zip5.length() > 5) zip5 = zip5.substring(0,5);
                String zip3 = zip5.substring(0,3);
                String bmcCode = MarketingHelper.findBMCCode(zip3, overrideBMC);

                Map input = UtilMisc.toMap("contactListId", contactListId, "contactMechId", address.get("contactMechId"));
                input.put("zip5", zip5);
                input.put("zip3", zip3);
                input.put("bmcCode", bmcCode);
                input.put("uspsContactListSortId", delegator.getNextSeqId("UspsContactListSort"));
                GenericValue sortValue = delegator.makeValue("UspsContactListSort", input);
                sortValue.create();
            }

            // Find zip5 codes with 10 or more addresses, set rows to sortResult="5-digit ZIP ${zip5}" and mark as processed
            List countFields = UtilMisc.toList("zip5", "contactMechId");
            List countOrderBy = UtilMisc.toList("zip5");
            EntityCondition condition = contactListCond;
            List zip5Groups = delegator.findByCondition("UspsContactListCountZip5", condition, null, countFields, countOrderBy, options);
            for (Iterator iter = zip5Groups.iterator(); iter.hasNext(); ) {
                GenericValue zip5Group = (GenericValue) iter.next();
                String zip5 = zip5Group.getString("zip5");
                Long count = zip5Group.getLong("contactMechId");
                if (count.longValue() < 10) continue;

                List addresses = delegator.findByAnd("UspsContactListSort", UtilMisc.toMap("contactListId", contactListId, "zip5", zip5));
                for (Iterator subIter = addresses.iterator(); subIter.hasNext(); ) {
                    address = (GenericValue) subIter.next();
                    address.set("sortResult", "5-digit ZIP " + zip5);
                    address.set("processedTimestamp", now);
                    address.store();
                }
            }

            // Find unprocessed zip3 codes with 10 or more addresses, set rows to sortResult="3-digit ZIP ${zip3}" and mark as processed
            countFields = UtilMisc.toList("zip3", "contactMechId");
            countOrderBy = UtilMisc.toList("zip3");
            condition = new EntityConditionList(UtilMisc.toList(contactListCond, notProcessedCond), EntityOperator.AND);
            List zip3Groups = delegator.findByCondition("UspsContactListCountZip3", condition, null, countFields, countOrderBy, options);
            for (Iterator iter = zip3Groups.iterator(); iter.hasNext(); ) {
                GenericValue zip3Group = (GenericValue) iter.next();
                String zip3 = zip3Group.getString("zip3");
                Long count = zip3Group.getLong("contactMechId");
                if (count.longValue() < 10) continue;

                condition = new EntityConditionList(UtilMisc.toList(
                            contactListCond,
                            notProcessedCond,
                            new EntityExpr("zip3", EntityOperator.EQUALS, zip3)
                            ), EntityOperator.AND);
                List addresses = delegator.findByCondition("UspsContactListSort", condition, null, null);
                for (Iterator subIter = addresses.iterator(); subIter.hasNext(); ) {
                    address = (GenericValue) subIter.next();
                    address.set("sortResult", "3-digit ZIP " + zip3);
                    address.set("processedTimestamp", now);
                    address.store();
                }
            }

            // Find unprocessed BMC codes with 10 or more addresses, set rows to sortResult=UspsBmcCode.description and mark as processed
            countFields = UtilMisc.toList("bmcCode", "contactMechId");
            countOrderBy = UtilMisc.toList("bmcCode");
            condition = new EntityConditionList( UtilMisc.toList(
                        contactListCond,
                        notProcessedCond,
                        new EntityExpr("bmcCode", EntityOperator.NOT_EQUAL, null)
                        ), EntityOperator.AND);
            List bmcCodeGroups = delegator.findByCondition("UspsContactListCountBmc", condition, null, countFields, countOrderBy, options);
            for (Iterator iter = bmcCodeGroups.iterator(); iter.hasNext(); ) {
                GenericValue bmcCodeGroup = (GenericValue) iter.next();
                String bmcCode = bmcCodeGroup.getString("bmcCode");
                Long count = bmcCodeGroup.getLong("contactMechId");
                if (count.longValue() < 10) continue;

                GenericValue bmc = delegator.findByPrimaryKeyCache("UspsBmcCode", UtilMisc.toMap("bmcCode", bmcCode));
                if (bmc == null) {
                    Debug.logWarning("UspsBmcCode not defined for bmcCode ["+bmcCode+"]. Not processing these addresses.", module);
                    continue;
                }
                condition = new EntityConditionList(UtilMisc.toList(
                            contactListCond,
                            notProcessedCond,
                            new EntityExpr("bmcCode", EntityOperator.EQUALS, bmcCode)
                            ), EntityOperator.AND);
                List addresses = delegator.findByCondition("UspsContactListSort", condition, null, null);
                for (Iterator subIter = addresses.iterator(); subIter.hasNext(); ) {
                    address = (GenericValue) subIter.next();
                    address.set("sortResult", bmc.getString("description"));
                    address.set("processedTimestamp", now);
                    address.store();
                }
            }

            // the final step is to select those where sortResult is null and set those to sortResult "Mixed States BMC"
            List addresses = delegator.findByAnd("UspsContactListSort", UtilMisc.toMap("contactListId", contactListId, "sortResult", null, "processedTimestamp", null));
            for (Iterator iter = addresses.iterator(); iter.hasNext(); ) {
                address = (GenericValue) iter.next();
                address.set("sortResult", "Mixed States BMC");
                address.set("processedTimestamp", now);
                address.store();
            }

            // Sort the international addresses into an OTHER bmc category
            orderBy = UtilMisc.toList("countryGeoId","postalCode");
            conditions = new EntityConditionList( UtilMisc.toList(
                        contactListCond,
                        EntityUtil.getFilterByDateExpr(),
                        new EntityExpr("statusId", EntityOperator.EQUALS, "CLPT_ACCEPTED"),
                        new EntityExpr("countryGeoId", EntityOperator.NOT_EQUAL, "USA")
                        ), EntityOperator.AND);
            iterator = delegator.findListIteratorByCondition("ContactListPartyAndAddress", conditions, null, null, orderBy, options);
            while ((address = (GenericValue) iterator.next()) != null) {
                Map input = UtilMisc.toMap("contactListId", contactListId, "contactMechId", address.get("contactMechId"));
                input.put("sortResult", "OTHER");
                input.put("bmcCode", "OTHER");
                input.put("processedTimestamp", now);
                input.put("uspsContactListSortId", delegator.getNextSeqId("UspsContactListSort"));
                GenericValue sortValue = delegator.makeValue("UspsContactListSort", input);
                sortValue.create();
            }
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, "CrmErrorAddNewCatalogRequests", locale, module);
        }

        return ServiceUtil.returnSuccess();
    }

    /**
     * Retrieves the DNIS of the latest call for the user from the FacetPhone server and matches it against a TrackingCode. Returns error only if VoIP.properties is
     *  misconfigured - otherwise returns failure if something goes wrong.
     * @param dctx DispatchContext
     * @param context Map
     * @return Map, possibly containing a TrackingCode GenericValue
     */
    public static Map retrieveTrackingCodeFromFacetPhoneServer(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");

        // Make sure FacetPhone integration is turned on
        boolean facetPhoneIntegrate = "true".equalsIgnoreCase( UtilProperties.getPropertyValue("VoIP", "facetPhone.integrate", ""));
        if (! facetPhoneIntegrate) return ServiceUtil.returnSuccess();

        // Check the configuration settings
        String callStateRegexp = UtilProperties.getPropertyValue("VoIP", "facetPhone.cid.callState.regexp");
        if (UtilValidate.isEmpty(callStateRegexp)) {
            return ServiceUtil.returnError(UtilProperties.getMessage(errorResource, "CrmErrorPropertyNotConfigured", UtilMisc.toMap("propertyName", "facetPhone.cid.callState.regexp", "fileName", voipResource + ".properties"), locale));
        }

        String dnisRegexp = UtilProperties.getPropertyValue("VoIP", "facetPhone.cid.dnis.regexp");
        if (UtilValidate.isEmpty(dnisRegexp)) {
            return ServiceUtil.returnError(UtilProperties.getMessage(errorResource, "CrmErrorPropertyNotConfigured", UtilMisc.toMap("propertyName", "facetPhone.cid.dnis.regexp", "fileName", voipResource + ".properties"), locale));
        }

        // Call the retrieveLatestCallFromFacetPhoneServer service
        String latestCallData = null;
        Map retrieveLatestCallFromFacetPhoneServerMap = null;
        try {
            retrieveLatestCallFromFacetPhoneServerMap = dispatcher.runSync("retrieveLatestCallFromFacetPhoneServer", UtilMisc.toMap("userLogin", userLogin, "locale", locale));
        } catch( GenericServiceException e ) {
            Debug.logError(ServiceUtil.getErrorMessage(retrieveLatestCallFromFacetPhoneServerMap), module);
            return ServiceUtil.returnFailure(ServiceUtil.getErrorMessage(retrieveLatestCallFromFacetPhoneServerMap));
        }
        if (ServiceUtil.isError(retrieveLatestCallFromFacetPhoneServerMap) || ServiceUtil.isFailure(retrieveLatestCallFromFacetPhoneServerMap)) {
            Debug.logError(ServiceUtil.getErrorMessage(retrieveLatestCallFromFacetPhoneServerMap), module);
            return ServiceUtil.returnFailure(ServiceUtil.getErrorMessage(retrieveLatestCallFromFacetPhoneServerMap));
        }
        
        latestCallData = (String) retrieveLatestCallFromFacetPhoneServerMap.get("latestCallData");
        if (UtilValidate.isEmpty(latestCallData)) {
            String errorMessage = UtilProperties.getMessage(errorResource, "CrmErrorVoIPErrorLatestCallFromFacetPhone", locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnFailure(errorMessage);
        }

        // Check the state of the call by retrieving it from the latestCallData via regular expression - probably in the form <state=(completed|active)>
        String state = "";
        Matcher stateMatcher = Pattern.compile(callStateRegexp).matcher(latestCallData);
        while (stateMatcher.find()) {
            if (stateMatcher.group(1) != null) state = stateMatcher.group(1);
        }
        
        // Ignore the results if there's no active call
        if (! "active".equalsIgnoreCase(state)) return ServiceUtil.returnSuccess();
        
        // Retrieve the DNIS from the latestCallData via regular expression - probably in the form <dnis=(\d+)>
        String dnis = null;
        Matcher dnisMatcher = Pattern.compile(dnisRegexp).matcher(latestCallData);
        while (dnisMatcher.find()) {
            if (dnisMatcher.group(1) != null) dnis = dnisMatcher.group(1);
        }
        if (UtilValidate.isEmpty(dnis)) {
            String errorMessage = UtilProperties.getMessage(errorResource, "CrmErrorVoIPErrorDNISFromFacetPhone", UtilMisc.toMap("latestCallData", latestCallData), locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnFailure(errorMessage);
        }

        // DNIS is stored in an extended field of TrackingCode - get the most recent match
        List trackingCodes = null;
        try {
            trackingCodes = delegator.findByAnd("TrackingCode", UtilMisc.toMap("dnis", dnis), UtilMisc.toList("-lastUpdatedStamp"));
        } catch( GenericEntityException e ) {
            Debug.logError(e, module);
            return ServiceUtil.returnFailure(e.getMessage());
        }

        GenericValue trackingCode = EntityUtil.getFirst(trackingCodes);
        if (UtilValidate.isEmpty(trackingCode)) {
            String errorMessage = UtilProperties.getMessage(errorResource, "CrmErrorVoIPErrorNoDNISMatchTrackingCode", UtilMisc.toMap("dnis", dnis, "latestCallData", latestCallData), locale);
            Debug.logError(errorMessage, module);
            return ServiceUtil.returnFailure(errorMessage);
        }

        Map result = ServiceUtil.returnSuccess();
        result.put("trackingCode", trackingCode);
        return result;
    }

    public static Map completeCatalogMailing(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");
        String contactListId = (String) context.get("contactListId");
        Timestamp now = UtilDateTime.nowTimestamp();

        try {
            
            // Verify that the contact list is POSTAL_ADDRESS and MARKETING
            GenericValue contactList = delegator.findByPrimaryKey("ContactList", UtilMisc.toMap("contactListId", contactListId));
            if (contactList == null) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotFound", UtilMisc.toMap("contactListId", contactListId), locale, module);
            }
            if (!"POSTAL_ADDRESS".equals(contactList.get("contactMechTypeId"))) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotAddress", contactList.getAllFields(), locale, module);
            }
            if (!"MARKETING".equals(contactList.get("contactListTypeId"))) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotMarketing", contactList.getAllFields(), locale, module);
            }
            
            List cond = new ArrayList();
            cond.add(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId));
            cond.add(new EntityExpr("statusId", EntityOperator.EQUALS, "CLPT_ACCEPTED"));
            cond.add(EntityUtil.getFilterByDateExpr());
            EntityListIterator clpit = delegator.findListIteratorByCondition("ContactListParty", new EntityConditionList(cond, EntityOperator.AND), null, null);
            
            GenericValue contactListParty = null;
            while ((contactListParty = (GenericValue) clpit.next()) != null) {
                Map custRequestMap = UtilMisc.toMap("custRequestTypeId", "RF_CATALOG", "statusId", "CRQ_ACCEPTED");
                
                String partyId = contactListParty.getString("partyId");
                custRequestMap.put("fromPartyId", partyId);
                String contactMechId = contactListParty.getString("preferredContactMechId");
                custRequestMap.put("fulfillContactMechId", contactMechId);
                
                // Update the CustRequest
                List custRequests = delegator.findByAnd("CustRequest", custRequestMap);
                Iterator crit = custRequests.iterator();
                while (crit.hasNext()) {
                    GenericValue custRequest = (GenericValue) crit.next();
                    custRequestMap.put("statusId", "CRQ_COMPLETED");
                    custRequestMap.put("fulfilledDateTime", now);
                    custRequestMap.put("userLogin", userLogin);
                    custRequestMap.put("locale", locale);
                    custRequestMap.put("custRequestId", custRequest.get("custRequestId"));
                    dispatcher.runSync("updateCustRequest", custRequestMap);
                }
                
                // Update the ContactListParty
                contactListParty.set("thruDate", now);
                contactListParty.store();
            }
            clpit.close();

        } catch (GenericEntityException gee) {
            return UtilMessage.createAndLogServiceError(gee, "CrmErrorCompleteCatalogMailing", locale, module);
        } catch (GenericServiceException gse) {
            return UtilMessage.createAndLogServiceError(gse, "CrmErrorCompleteCatalogMailing", locale, module);
        }

        return ServiceUtil.returnSuccess();
    }

    /**
     * Associate a contact list to a Marketing campaign with a tracking code
     * - checks if the tracking code already exists (expired or not, and only if given)
     * - checks the contact list is not already associated to this Marketing campaign
     * - checks the CoantctList and MarketingCampaign entities can be found
     * - create the MarketingCampaignContactList and TrackingCode
     * - returns the campaignListId
     */
    public static Map addContactListToMarketingCampaign(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");

        String contactListId = (String) context.get("contactListId");
        String marketingCampaignId = (String) context.get("marketingCampaignId");
        String trackingCode = (String) context.get("trackingCode");
        Timestamp now = UtilDateTime.nowTimestamp();

        try {
            // check the tracking code is not already use (if given)
            if (UtilValidate.isNotEmpty(trackingCode)) {
                GenericValue tc = delegator.findByPrimaryKey("TrackingCode", UtilMisc.toMap("trackingCodeId", trackingCode));
                if (UtilValidate.isNotEmpty(tc)) {
                    return UtilMessage.createAndLogServiceError("CrmErrorTrackingCodeAlreadyUsed", UtilMisc.toMap("trackingCode", trackingCode), locale, module);
                }
            }

            // check the list is not already on this marketing campaign
            List<GenericValue> assocs = EntityUtil.filterByDate(delegator.findByAnd("MarketingCampaignContactList", UtilMisc.toMap("contactListId", contactListId, "marketingCampaignId", marketingCampaignId)));
            if (UtilValidate.isNotEmpty(assocs)) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListAlreadyAssociatedToMarketingCampaign", UtilMisc.toMap("contactListId", contactListId), locale, module);
            }

            // check entities
            GenericValue contactList = delegator.findByPrimaryKeyCache("ContactList", UtilMisc.toMap("contactListId", contactListId));
            if (UtilValidate.isEmpty(contactList)) {
                return UtilMessage.createAndLogServiceError("CrmErrorContactListNotFound", UtilMisc.toMap("contactListId", contactListId), locale, module);
            }
            GenericValue marketingCampaign = delegator.findByPrimaryKeyCache("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
            if (UtilValidate.isEmpty(marketingCampaign)) {
                return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignNotFound", UtilMisc.toMap("marketingCampaignId", marketingCampaignId), locale, module);
            }
            
            // all set, create the MarketingCampaignContactList
            String campaignListId = delegator.getNextSeqId("MarketingCampaignContactList");
            delegator.create("MarketingCampaignContactList", UtilMisc.toMap("campaignListId", campaignListId, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId, "fromDate", now));
            // then create the TrackingCode (if given)
            if (UtilValidate.isNotEmpty(trackingCode)) {
                delegator.create("TrackingCode", UtilMisc.toMap("trackingCodeId", trackingCode, "trackingCodeTypeId", "INTERNAL", "campaignListId", campaignListId, "marketingCampaignId", marketingCampaignId, "contactListId", contactListId, "fromDate", now));
            }

            // return success
            Map result = ServiceUtil.returnSuccess();
            result.put("campaignListId", campaignListId);
            return result;
        } catch (GenericEntityException e2) {
            return UtilMessage.createAndLogServiceError(e2, module);
        }
    }

    /**
     * Removes a contact list from a Marketing campaign with its tracking code by expiring those entities
     * - expire the MarketingCampaignContactList
     * - expire the TrackingCodes associated to it
     */
    public static Map removeContactListFromMarketingCampaign(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();

        Locale locale = (Locale) context.get("locale");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String campaignListId = (String) context.get("campaignListId");
        Timestamp now = UtilDateTime.nowTimestamp();

        try {
            // get the MarketingCampaignContactList
            GenericValue marketingCampaignContactList = delegator.findByPrimaryKey("MarketingCampaignContactList", UtilMisc.toMap("campaignListId", campaignListId));
            if (UtilValidate.isEmpty(marketingCampaignContactList)) {
                return UtilMessage.createAndLogServiceError("CrmErrorMarketingCampaignContactListNotFound", UtilMisc.toMap("campaignListId", campaignListId), locale, module);
            }
            // expire it
            if (UtilValidate.isEmpty(marketingCampaignContactList.get("thruDate"))) {
                marketingCampaignContactList.set("thruDate", now);
                delegator.store(marketingCampaignContactList);
            }

            // get the TrackingCodes and expire them
            List<GenericValue> trackingCodes = delegator.findByAnd("TrackingCode", UtilMisc.toMap("campaignListId", campaignListId));
            for (GenericValue tc : trackingCodes) {
                if (UtilValidate.isEmpty(tc.get("thruDate"))) {
                    tc.set("thruDate", now);
                    delegator.store(tc);
                }
            }

            // return success
            return ServiceUtil.returnSuccess();
        } catch (GenericEntityException e2) {
            return UtilMessage.createAndLogServiceError(e2, module);
        }
    }

    /**
     * Creates a ContactList
     * - if a marketingCampaignId is given, make the association
     */
    public static Map createContactList(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();

        Locale locale = (Locale) context.get("locale");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String marketingCampaignId = (String)context.get("marketingCampaignId");

        try {
            // create the contact list without marketingCampaignId
            String contactListId = delegator.getNextSeqId("ContactList");
            GenericValue contactList = delegator.makeValidValue("ContactList", context);
            contactList.put("contactListId", contactListId);
            contactList.put("marketingCampaignId", null);
            delegator.create(contactList);

            // associate if marketingCampaignId was given
            if (UtilValidate.isNotEmpty(marketingCampaignId)) {
                Map serviceResults = dispatcher.runSync("crmsfa.addContactListToMarketingCampaign", UtilMisc.toMap("contactListId", contactListId , "marketingCampaignId", marketingCampaignId, "userLogin", userLogin));
                if (!UtilCommon.isSuccess(serviceResults)) {
                    return serviceResults;
                }
            }

            // return success
            Map result = ServiceUtil.returnSuccess();
            result.put("contactListId", contactListId);
            return result;
        } catch (GenericEntityException e) {
            return UtilMessage.createAndLogServiceError(e, module);
        } catch (GenericServiceException e) {
            return UtilMessage.createAndLogServiceError(e, module);
        }
    }

    /**
     * Migrate old ContactList association to MarketingCampaign to the new N-N relationship.
     *  checks all ContactList that have a non null marketingCampaignId, removes it and creates
     *  an active MarketingCampaignContactList entity instead.
     *  The MarketingCampaignContactList is active from the ContactList created stamp. 
     */
    public static Map migrateContactLists(DispatchContext dctx, Map context) {
        GenericDelegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();

        Locale locale = (Locale) context.get("locale");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        EntityListIterator contactListIterator = null;

        Long success = new Long(0);
        Long error = new Long(0);

        try {
            TransactionUtil.begin();
            EntityConditionList conditions = new EntityConditionList( UtilMisc.toList(new EntityExpr("marketingCampaignId", EntityOperator.NOT_EQUAL, null)), EntityOperator.AND);
            contactListIterator = delegator.findListIteratorByCondition("ContactList", conditions, null, null);
            TransactionUtil.commit();

            if (contactListIterator == null) {
                return UtilMessage.createAndLogServiceError("List iterator was null", module);
            }

            GenericValue contactList = null;
            String marketingCampaignId;
            String contactListId;
            while ((contactList = (GenericValue) contactListIterator.next()) != null) {
                try {
                    TransactionUtil.begin();
                    
                    marketingCampaignId = contactList.getString("marketingCampaignId");
                    contactListId = contactList.getString("contactListId");
                    Debug.logInfo("Migrating ContactList ["+contactListId+"]", module);
                    
                    // remove marketingCampaignId
                    contactList.put("marketingCampaignId", null);
                    delegator.store(contactList);
                    
                    // create association
                    Map serviceResults = dispatcher.runSync("crmsfa.addContactListToMarketingCampaign", UtilMisc.toMap("contactListId", contactListId, "marketingCampaignId", marketingCampaignId, "userLogin", userLogin));
                    
                    // set the from date
                    if (!UtilCommon.isSuccess(serviceResults)) {
                        Debug.logError("crmsfa.addContactListToMarketingCampaign did not return success for contactList ["+contactListId+"]", module);
                        error++;
                        TransactionUtil.rollback();
                    } else {
                        // set the active from date to the contactList created date
                        String campaignListId = (String)serviceResults.get("campaignListId");
                        GenericValue link = delegator.findByPrimaryKey("MarketingCampaignContactList", UtilMisc.toMap("campaignListId", campaignListId));
                        link.put("fromDate", contactList.get("createdStamp"));

                        delegator.store(link);
                    
                        TransactionUtil.commit();
                        success++;
                    }
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    error++;
                    TransactionUtil.rollback();
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    error++;
                    TransactionUtil.rollback();
                }

            }
            contactListIterator.close();

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            error++;
        }

        Map result = ServiceUtil.returnSuccess();
        result.put("success", success);
        result.put("error", error);
        return result;
    }

}
