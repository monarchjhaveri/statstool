package com.sitescout.statstool;

import com.sitescout.dsp.db.dbpools.DbPools;
import com.sitescout.dsp.stats.model.CampaignStatistic;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App
{
    public static void main( String[] args ) throws ParseException, FileNotFoundException, InterruptedException {
        PrintStream stdout = System.out;

        System.out.printf("Starting to populate your database...\n");

        System.setOut(
            new PrintStream(
                new FileOutputStream("statstool-error.log", true)));

        System.setOut(
            new PrintStream(
                new FileOutputStream("statstool.log", true)));

        Context context = Context.getInstance();
        CampaignStatisticWriter campaignStatisticWriter = context.getCampaignStatisticWriter();

        try {
            Options options = defineOptions();
            Arguments commandLine = parseArguments(args, options);

            if (!commandLine.isValid()) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(" ", options, true);
                return;
            }

            List<CampaignStatistic> campaignStatisticsList = Stream
                .generate(() -> CampaignStatisticGenerator.randomCampaignStatistic(
                    commandLine.getAdvertiserId(),
                    commandLine.getCampaignId(),
                    commandLine.getNetworkId(),
                    commandLine.getAdId(),
                    commandLine.getSiteId()
                ))
                .limit(commandLine.getQuantity())
                .collect(Collectors.toList());

            campaignStatisticWriter.write(campaignStatisticsList);
        } finally {
            campaignStatisticWriter.prepareForShutdown();

            ScheduledExecutorService executorService = context.getExecutorService();
            DbPools.INSTANCE.shutdown();
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.SECONDS);
            System.setOut(stdout);
            System.out.printf("Completed populating your database.\n");
        }
    }

    private static Arguments parseArguments(String[] args, Options options) throws ParseException {


        CommandLine commandLine = new DefaultParser().parse(options, args);
        return new Arguments(commandLine);
    }

    private static Option createOption(String longOpt, String description) {
        return OptionBuilder
            .withLongOpt(longOpt)
            .withDescription(description + " (Required >= 1)")
            .hasArg(true)
            .create();
    }

    private static Options defineOptions() {
        Options options = new Options();
        options.addOption(createOption("advertiserId", "Advertiser ID"));
        options.addOption(createOption("campaignId", "Campaign ID"));
        options.addOption(createOption("networkId", "Network ID"));
        options.addOption(createOption("adId", "Ad ID"));
        options.addOption(createOption("siteId", "Site ID"));
        options.addOption(createOption("quantity", "Number of stats records to create"));
        return options;
    }
}
