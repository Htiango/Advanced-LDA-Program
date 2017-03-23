Advanced Chinese LDA
================

/**
Copyright  by hty

Feel free to contact the following people if you find any
problems in the package.
hongty106@gmail.com * */

Brief Introduction
===================
1. This is Tianyu Hong's second version of a program using LDA to predict Chinese message. I use 2 models of LDA. One that is the normal LDA, the other is self-designed UQA model. It contains not only the topics, but also the user's expertise.

2. To use the program, you should have the "WindowBuilder" plugin on your Eclipse. 

3. I use the "Fudan NLP" package to separate the each message and get rid of all the stopwords. 

4. About the UQA model:  Based on the characters of the data after pre-processing, we come up with two new concepts, expert and expertise. With those two concepts as well as mathematic derivation, we come up with the new model, the UQA model, which makes it possible to analyze the distribution of what attracts expert to answer. UQA model can not only achieve best answer recommendation as the LDA model do, it can also recommend best expert to the user intelligently. We present the complete mathematic derivation of UQA model, as well as the formula of answer/expert recommendation using Bayesian Theorem.

5. The training data is crawled from online Healthcare Community.
