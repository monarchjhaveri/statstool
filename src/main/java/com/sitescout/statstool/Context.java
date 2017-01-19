package com.sitescout.statstool;

import com.sitescout.dsp.stats.model.CampaignStatistic;
import com.sitescout.dsp.stats.model.NetworkCampaignStatistic;
import com.sitescout.dsp.stats.writers.mysql.AnnotationBasedMySqlStatsWriter;
import com.sitescout.dsp.util.config.Config;
import com.sitescout.dsp.util.config.ConfigPathResolver;
import com.sitescout.dsp.util.config.LocalConfig;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Context {
    private static Context instance;

    private final ScheduledExecutorService executorService;
    private final Config config;
    private final CampaignStatisticWriter campaignStatisticWriter;

    private Context(ScheduledExecutorService executorService, Config config) {
        this.executorService = executorService;
        this.config = config;

        this.campaignStatisticWriter = new CampaignStatisticWriter(new AnnotationBasedMySqlStatsWriter<CampaignStatistic>(executorService, config, CampaignStatistic.class),
            new AnnotationBasedMySqlStatsWriter<NetworkCampaignStatistic>(executorService, config, NetworkCampaignStatistic.class));
    }

    public static Context getInstance() {
        if (instance == null) {
            try {
                instance = fromPropertiesFile("dbpools.properties");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    private static Context fromPropertiesFile(String propertiesPath) throws IOException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        LocalConfig localConfig = new LocalConfig(ConfigPathResolver.getFullPath(propertiesPath), executorService);
        return new Context(executorService, localConfig);
    }

    public CampaignStatisticWriter getCampaignStatisticWriter() {
        return campaignStatisticWriter;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
