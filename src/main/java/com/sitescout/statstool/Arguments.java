package com.sitescout.statstool;

import org.apache.commons.cli.CommandLine;

public class Arguments {
    private final CommandLine commandLine;

    private Integer advertiserId;
    private Integer campaignId;
    private Integer networkId;
    private Integer adId;
    private Integer siteId;
    private Integer quantity;
    private boolean isValid;

    public Arguments(CommandLine parse) {
        isValid = true;

        commandLine = parse;
        advertiserId = ensuredPositiveInteger("advertiserId");
        campaignId = ensuredPositiveInteger("campaignId");
        networkId = ensuredPositiveInteger("networkId");
        adId = ensuredPositiveInteger("adId");
        siteId = ensuredPositiveInteger("siteId");
        quantity = ensuredPositiveInteger("quantity");
    }

    public Integer getAdId() {
        return adId;
    }

    public Integer getAdvertiserId() {
        return advertiserId;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    private Integer ensuredPositiveInteger(String optionValue) {
        Integer value = null;

        String stringValue  = this.commandLine.getOptionValue(optionValue);
        try {
            value = stringValue == null ? null : Integer.parseInt(stringValue);
        } catch (NumberFormatException ignore) {}


        if (value == null || value <= 0) {
            isValid = false;
            return null;
        } else {
            return value;
        }
    }

    public boolean isValid() {
        return isValid;
    }
}
