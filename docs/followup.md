# Follow-Up Questions

1. **How long did you spend on the test?**
**R:** Something between 16 and 18 hours I think. I could manage to work on it for something around 4 hours a day for 4 days, maybe a little bit more.

2. **What would you add if you had more time?**
**R:** God, where do I start? I think OpenAPI standard documentations for the two apps would be the first thing, followed by some filters in the promotions-api search services (it would reduce a lot the processing). Setting up a propper circuit breaker in the checkout-api would also help a lot with the integrations.

3. **How would you improve the product APIs that you had to consume?**
**R:** The modeling is fine, I would just add a couple of filters in order to reduce the payload. I believe paging would also come in handy when presenting the products at the frontend.

4. **What did you find most dificult**
**R:** I created unnecessary complexy when modeling the promotions, what lead to a hard time applying said promotions to the incoming products. It was really hard to break down that `CheckoutService.checkout` method into smaller parts, and I'm still not very happy about the outcome, because I had to give up on performance to favour at least a little bit of legibility.

5. **How did you find the overall experience, any feedback for us?**
**R:** Actually, I had a lot of fun! Sometimes these challenges seem to be all about technique, but I really had to think about the business logic here, draw a solution to a real world problem. I got a little bit confused at first, didn't know exactelly how to handle the promotions, but it worked out just fine in the end.

