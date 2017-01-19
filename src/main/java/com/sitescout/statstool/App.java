package com.sitescout.statstool;

import com.sitescout.dsp.stats.model.CampaignStatistic;
import org.apache.commons.cli.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App
{
    public static void main( String[] args ) throws ParseException {
        Options options = defineOptions();
        Arguments commandLine = parseArguments(args, options);

        if (!commandLine.isValid()) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(" ", options, true);
            return;
        }

        Context context = Context.getInstance();
        CampaignStatisticWriter campaignStatisticWriter = context.getCampaignStatisticWriter();

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
