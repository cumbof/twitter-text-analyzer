# Naive-Bayes Classifier #

The Naive Bayesian classifier is a straightforward and frequently used method for supervised learning. It provides a flexible way for dealing with any number of attributes or classes, and is based on probability theory. It is the asymptotically fastest learning algorithm that examines all its training input. The Naive Bayesian classification system is based on Bayes rule and works as follows:

```
P(c|t) = P(c)*P(t|c)/P(t)
```

It assumes each feature is conditional independent to other features given the class. That is, where c is a specific
class and t is text we want to classify. P(c) and P(t) is the prior probabilities of this class and this text
and P(t|c) is the probability the text appears given this class. In our case, the volume of class c might be
positive or negative, and t is just a sentence. The goal is choosing value of c to maximize P(c|t): Where P(wi|c)
is the probability of the ith feature in text t appears given class c. We need to train parameters P(c) and P(wi|c).
It is simple for getting these parameters in Naive Bayes model. They are just maximum likelihood estimation of
each one. When making prediction to a new sentence t, we calculate the log likelihood log(P(c)) + log(P(wi|c)) of
different classes, and take the class with highest log likelihood as prediction. In practice, it needs smoothing to
avoid zero probabilities. Otherwise, the likelihood will be 0 if there is an unseen word when it making prediction.
Using add-1 smoothing is a solution to that problem.