I don't want to miss a thing
Note: Use your unit project to do the following tasks.


***Milestone 1: Log Metrics***

You’ve just created a cache to support RecommendationsService calls, which has helped bring down the latency of GetBook! You can check out the changes in GetBook’s latency by viewing its metrics on your CloudWatch console. To view GetBook’s metrics:

Run your tests t generate some calls to your GetBook operation (or feel free to generate some traffic of your own)
When they are finished running, log in to your AWS console
Make sure the region at the top right is set to US West (Oregon)
Go to Services > CloudWatch
Click Metrics from the left-side menu
Click the atacurriculumkindlepublishingservice custom namespace
Click Operation, Program, Service. You should see various metrics for all of the different operations in your service.
Find and graph the GetBook operation’s Time metric
After graphing the metric, you should be able to see the latency metrics of GetBook. You may want to change the time window at the top so you can see metrics from a longer time range. You should see some latency datapoints that are much lower, which represent requests that hit your recommendations cache! Note that for your service, you'll see that there aren't that many metric datapoints. This is because your service currently doesn't get a lot of traffic. The only traffic it gets is from the TCTs and when you're manually calling the service for testing. Most production services have much more data in their metrics graphs, since they get more traffic from actual clients calling their service.

We want to collect more data around how our cache is performing. This will help determine how effective it is and whether we want to change any of our cache’s configuration. Some data you’d like to collect is how many times the cache is called and how many times RecommendationService is called. These metrics will help us understand how often recommendations were successfully retrieved from the cache, and how often our service calls our RecommendationsServiceClient to load recommendations into our cache.

Your task here is to publish the necessary metrics to track 1) the number of times your service queries the cache (regardless of a hit or miss) and 2) how many times RecommendationsServiceClient getBookRecommendations is called. If we know both of these datapoints we can calculate the hit and miss rates. More on this calculation in milestone 2!

Coral provides a class called CloudWatchCoralMetricsReporter which helps publish logged metrics to CloudWatch for us. We’ve provided a MetricsPubisher helper class that utilizes the metrics reporter and provides two methods that allows you to publish count and time metrics to CloudWatch. You can see an example of how the MetricsPublisher is used to measure the latency of the getBookRecomendations call in our RecommendationsServiceClient.

Make sure to add the name of your new metric to the whitelist of metric names, called metricNames, in the MetricsModule class, otherwise the CloudWatchCoralMetricsReporter won’t publish your metric to CloudWatch!

Update your unit tests to verify calls are made to the MetricsPublisher.

To test manually, restart your service so you know your cache is empty. Then make two calls to the GetBook operation with the same bookId. The first call should result in a cache miss and call RecommendationsServiceClient to get the recommendations, and the second call should result in a cache hit. In your Unit 4 account, check the CloudWatch console to see if your new metrics appear. Your counts should reflect your testing behavior. For example, when running the above test we would see our cache query count total 2, and our RecommendationsServiceClient getBookRecommendations calls count total 1.

***Milestone 2: Create a dashboard***
Now that you’ve created metrics, you’ll also want a central place for your team to review them. One way to do this is by creating a metrics dashboard. Dashboards are used to display and monitor key metrics of a service in a single, central location. Development teams always have dashboards to display important service metrics like latency, number of errors, and request counts for each of the service’s APIs. This allows teams to quickly get an overview of the health of their service, which is useful when you want check in on a newly deployed feature or you want to see how a large scale event is affecting your service.

For this task, you will be creating a dashboard in CloudWatch to display cache metrics.

To create a new dashboard in CloudWatch, follow these steps:

Open CloudWatch in your AWS console.
Click Dashboards in the menu on the left.
Click Create dashboard.
Set the name of your dashboard to be KindlePublishingService_CacheDashboard and press submit.
In the "Add to this dashboard" window, select "Line: Compare metrics over time" and click Configure.
Search for your metric that counts the number of queries for your cache and add it to the graph (by clicking the checkbox on the left).
Update the tile of your graph to be "Cache Query Count" by clicking the pencil icon on the top left (next to "Untitled graph").
Click Create widget.
You should now see your new dashboard which has a graph of your Recommendations cache count metric! You can add more graphs to your dashboard by clicking “Add widget” at the top of your dashboard and following steps #5-8 above. Do this for your RecommendationsServiceClient's getBookRecommendations call count metric that you created and title the graph "Cache Miss Count". Now you should have two graphs on your dashboard for both of the metrics that you created.

Note: When creating dashboards and calculating metric math, make sure to choose an appropriate statistic for your metrics, CloudWatch defaults to using the 'average' of the data i.e. sum of the data values divided by how many times the data was emitted within the sample period.

Now we want to graph our cache hit rate metric, which is the percentage of calls to the cache the results in a cache hit. Although you didn’t log the cache hits count explicitly in your code, you can actually use the two metrics that you did create to calculate the cache hit rate! We know that any cache miss results in a call to RecommendationsServiceClient's getBookRecommendations, which we now have a count of. We also know the total number of times a request was made to our cache, because we are counting that too. So, all you need to do is subtract the number of times RecommendationsServiceClient's getBookRecommendations was called from the number of times the cache was queried to get the number of cache hits. The cache hit rate would be the number of cache hits divided by the number of queries to the cache.

So, how do you create a new graph based on calculations of two other metrics? CloudWatch allows you to perform metric math, which lets you use math expressions on metrics to create new metrics. View CloudWatch’s documentation on metric math (Links to an external site.) for instructions on how to add a math expression on a graph and more details on the different math functions it supports.

For your dashboard, create a graph representing the cache hit rate using the metric math calculation described above. You would start by creating the graph the same way you did with the other two graphs in your dashboard: click “Add widget” and search for the two count metrics you created and add them to your graph. Then go to the "Graphed metrics" tab and click on "Math expression" and then "Start with empty expression." In the graphs table you should see another row for the math expression. Rename the expression to be 'Recommendations Cache Hit Rate'. Update the details section of the math expression with your metric math. Note that there was also an Id column added. You can use the Id values to refer to a specific metric in your metric math expression (e.g. m1+m2 to graph the sum of m1 and m2). Make sure to uncheck m1 and m2 so that just the expression metric shows up on the graph. Title this graph "Cache Hit Rate".

Save your dashboard by going to Actions > Save dashboard at the top of your dashboard page. You can access the dashboard's source code by going to Actions > View/edit source, which will give you a JSON representation of the dashboard. Then update the mastery-task6-dashboard.txt in the resources directory by pasting in the source code of your dashboard.

Doneness Checklist:

- You’ve implemented functionality that creates and publishes a metric that counts the number of times the cache is called and the number of time RecommendationsService is called.
- You’ve added/updated unit tests that verify that the MetricsPublisher was called as expected.
- You have a CloudWatch dashboard that includes graphs of the metrics you created and the cache hit rate.