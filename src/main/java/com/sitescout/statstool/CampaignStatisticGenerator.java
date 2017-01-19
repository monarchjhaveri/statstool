package com.sitescout.statstool;

import com.sitescout.dsp.stats.model.CampaignStatistic;
import com.sitescout.dsp.stats.model.StatsEnums;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class CampaignStatisticGenerator {
    public static CampaignStatistic randomCampaignStatistic(
        Integer advertiserId,
        Integer campaignId,
        Integer networkId,
        Integer adId,
        Integer siteId
    ) {
        CampaignStatistic campaignStatistic = new CampaignStatistic();
        campaignStatistic.setAdvertiserId(advertiserId);
        campaignStatistic.setCampaignId(campaignId);
        campaignStatistic.setNetworkId(networkId);
        campaignStatistic.setAdId(adId);
        campaignStatistic.setSiteId(siteId);

        Random random = ThreadLocalRandom.current();

        campaignStatistic.setDate(DateTime.now().withTimeAtStartOfDay().toDate());
        campaignStatistic.setDimensions("300x250");
        campaignStatistic.setPagePos(StatsEnums.PagePosition.UNKNOWN);
        campaignStatistic.setClicks(1);
        campaignStatistic.setOfferClicks(1);
        campaignStatistic.setConversions(1);
        campaignStatistic.setViewthruConversions(1);
        campaignStatistic.setPartnerRevenue(random.nextDouble());
        campaignStatistic.setVtcRevenue(random.nextDouble());
        campaignStatistic.setVideoStarted(1);
        campaignStatistic.setVideoMidPointReached(1);
        campaignStatistic.setVideoFirstQuartileReached(1);
        campaignStatistic.setVideoSkipped(1);
        campaignStatistic.setVideoThirdQuartileReached(1);
        campaignStatistic.setVideoCompleted(1);
        campaignStatistic.setAuctionsBid(7);
        campaignStatistic.setCost(random.nextDouble());
        campaignStatistic.setDataCost(random.nextDouble());
        campaignStatistic.setOfferRevenue(random.nextDouble());
        campaignStatistic.setRevenue(random.nextDouble());
        campaignStatistic.setAuctionsWon(5);
        campaignStatistic.setDomain(StringUtils.EMPTY);
        return campaignStatistic;
    }
}
