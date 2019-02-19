# IPSCResultServer

A Java Web server for an IPSC shooting match result service. 

The server receives result data from an Android uploader App (uploading result data from PractiScore scoring app to server). The uploader is in Git repository https://github.com/jarnovirta/AndroidPractiScoreUploader. The uploader fetches match data from a [PractiScore](https://practiscore.com/) match data export file (.psc) and uploads the data to the server. Matches can be scored using PractiScore and the results quickly uploaded to the server for live match result service during the match.

The web pages show match results, competitor statistics, as well as match analysis for competitors done using Google Charts. The web pages include a live match result service view showing current results for a match, to be displayed on a public monitor during a match.

Server running on www.hitfactor.fi.
