Usage:

```
mvn clean install;

java -jar target/statstool-1.0-SNAPSHOT.jar \
    --advertiserId 2 \
    --campaignId 110347 \
    --siteId 12778 \
    --networkId 1 \
    --adId 110467 \
    --quantity 100
```

All params are required
"Quantity" is the number of entities to create. Each quantity unit creates 1 CampaignStatistic and 1 NetworkCampaignStatistic.