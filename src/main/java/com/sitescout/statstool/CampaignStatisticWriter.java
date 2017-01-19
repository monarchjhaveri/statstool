package com.sitescout.statstool;

import com.sitescout.dsp.stats.model.CampaignStatistic;
import com.sitescout.dsp.stats.model.NetworkCampaignStatistic;
import com.sitescout.dsp.stats.writers.mysql.AnnotationBasedMySqlStatsWriter;

import java.util.List;
import java.util.stream.Collectors;

public class CampaignStatisticWriter {
    private final AnnotationBasedMySqlStatsWriter campaignWriter;
    private final AnnotationBasedMySqlStatsWriter networkCampaignWriter;

    public CampaignStatisticWriter(AnnotationBasedMySqlStatsWriter campaignWriter, AnnotationBasedMySqlStatsWriter networkCampaignWriter) {
        this.campaignWriter = campaignWriter;

        this.networkCampaignWriter = networkCampaignWriter;
    }

    @SuppressWarnings("unchecked")
    public void write(List<CampaignStatistic> campaignStatistics) {
        List<NetworkCampaignStatistic> networkCampaignStatistics = campaignStatistics.stream()
            .map(CampaignStatisticWriter::transform)
            .collect(Collectors.toList());

        campaignWriter.writeStats(campaignStatistics);
        networkCampaignWriter.writeStats(networkCampaignStatistics);
    }

    private static NetworkCampaignStatistic transform(CampaignStatistic campaignStatistic) {
        NetworkCampaignStatistic networkCampaignStatistic = new NetworkCampaignStatistic();
        networkCampaignStatistic.setCampaignId(campaignStatistic.getCampaignId());
        networkCampaignStatistic.setNetworkId(campaignStatistic.getNetworkId());
        networkCampaignStatistic.setAdId(campaignStatistic.getAdId());
        networkCampaignStatistic.setAdvertiserId(campaignStatistic.getAdvertiserId());
        networkCampaignStatistic.setDate(campaignStatistic.getDate());
        networkCampaignStatistic.setAuctionsBid(campaignStatistic.getAuctionsBid());
        networkCampaignStatistic.setAuctionsWon(campaignStatistic.getAuctionsWon());
        networkCampaignStatistic.setClicks(campaignStatistic.getClicks());
        networkCampaignStatistic.setOfferClicks(campaignStatistic.getOfferClicks());
        networkCampaignStatistic.setConversions(campaignStatistic.getConversions());
        networkCampaignStatistic.setOfferRevenue(campaignStatistic.getOfferRevenue());
        networkCampaignStatistic.setCost(campaignStatistic.getCost());
        networkCampaignStatistic.setDataCost(campaignStatistic.getDataCost());
        networkCampaignStatistic.setRevenue(campaignStatistic.getRevenue());
        networkCampaignStatistic.setPartnerRevenue(campaignStatistic.getPartnerRevenue());
        networkCampaignStatistic.setViewthruConversions(campaignStatistic.getViewthruConversions());
        networkCampaignStatistic.setVtcRevenue(campaignStatistic.getVtcRevenue());
        networkCampaignStatistic.setVideoStarted(campaignStatistic.getVideoStarted());
        networkCampaignStatistic.setVideoFirstQuartileReached(campaignStatistic.getVideoFirstQuartileReached());
        networkCampaignStatistic.setVideoMidPointReached(campaignStatistic.getVideoMidPointReached());
        networkCampaignStatistic.setVideoThirdQuartileReached(campaignStatistic.getVideoThirdQuartileReached());
        networkCampaignStatistic.setVideoCompleted(campaignStatistic.getVideoCompleted());
        networkCampaignStatistic.setVideoSkipped(campaignStatistic.getVideoSkipped());
        return networkCampaignStatistic;
    }
}
