# Feature Reduction & Preprocessing #

  * **Language:** The TwitterTextAnalizer only consider english language tweets.

  * **URL:** In order to reduce the feature size during training, all URLs in the training tweets were removed.

  * **Emoticons:** All emoticons are stripped from the training set.

  * **Usernames & Hashtags:** Usernames in twitter are given in the '@username' format. And similarly, people tag tweets pertaining to a category in twitter, using ''. We remove all usernames and hashtags from all tweets.

  * **Uppercasing:** All the characters were uppercased to ensure that all tokens map to the corresponding feature irrespective of casing.

  * **Special Characters:** We remove all special characters from tweets.