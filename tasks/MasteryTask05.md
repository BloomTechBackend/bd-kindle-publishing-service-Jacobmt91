###Recommendations - a Cache Cow
Note: Use your unit project to do the following tasks.

The product team has informed your dev team that there have been a lot of customer complaints about waiting for Kindle detail pages to load. The feedback is our GetBook operation is very slow, taking on average 7 seconds to respond! Based on our metrics, we know that retrieving recommendations from the Recommendation Service is responsible for about 97% of the latency. Someone on your team suggests getting rid of the recommendations in order to speed up the call. They help generate a LOT of revenue, but if customers don’t wait for the detail page to load they aren’t very useful! If only we could speed things up...

You mention to your team that you think we could use a cache to help speed things up. They think it’s a great idea and leave you to design and implement it. Your team has implemented recommendations based on the genre of the book. You first check with your product team if it’s ok that recommendations aren’t freshly generated on each kindle detail page load. They are fine with the recommendations being the same for up to 10 minutes. After that they think that buyer trends within the genre may have changed and they’d like to provide customers with new recommendations.

***Milestone 1: Design your cache***

Design work is often collaborative - so we suggest you grab a peer and discuss how you plan to implement your cache. A whiteboard is a great place to chat. You will separately need to respond to the questions in resources/mastery-task5-cache-design.md. You should also update the GetBook sequence diagram. The current sequence diagram source code from the design doc is in a file, resources/mastery-task5-get-book-SD.puml. Make the changes here to incorporate the cache into the sequence diagram. We want to be able to easily switch back and forth between the cached and uncached versions in case future requirements change or we run into any problems.

***Milestone 2: Implement your cache***

Your senior engineer approves your design, let’s get coding! Go ahead and implement your design. Don’t forget to add unit tests!

When implementing cached calls, consider which classes should be a singletons. What would the behavior be if your cache is not treated as a singleton i.e. our DI framework creates a new cache instance every time it is needed?

Exit Checklist:

You’ve implemented your caching functionality
