# Resources

## Git Workflow

git commit -am "Your commit Message Here"  
git pull  
merge things there if necessary  
commit again if you resolved merge conflicts  
git push  

this is a test

## When2Meet's

[General](https://www.when2meet.com/?8367342-GaTih)

## Project-related

### Webpages
* [Heineman's Storyboard](https://web.cs.wpi.edu/~heineman/cs3733/)
* [W3 Schools on the Video tag](https://www.w3schools.com/tags/tag_video.asp)
* [Learn HTML](https://www.w3schools.com/html/default.asp)
* [Learn CSS](https://www.w3schools.com/css/default.asp)
* [Learn JavaScript](https://www.w3schools.com/js/default.asp)
* [Have you ever wanted to do a small, particular thing in JS?](https://www.w3schools.com/howto/default.asp)
* [How exactly does JSON work?](https://www.w3schools.com/Js/js_json_intro.asp)
* [Query Strings](https://www.w3schools.com/asp/coll_querystring.asp)
* [Styling a Checkbox](https://www.geeksforgeeks.org/how-to-style-a-checkbox-using-css/)
* [SQL Regular expressions](https://dev.mysql.com/doc/refman/5.5/en/regexp.html)

### AWS
* To use the Amazon Project (Lydia AWS) in Eclipse, it's easier if you open it as 
  its own project rather than as a subfolder in CS3733-Lydia.

### Java
* [UUID Documentation](https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html)
  * String representations are exactly 36 characters long
  * Snippet to generate: String testIDString = UUID.randomUUID().toString();
* [DB RequestSet Object](https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html)

## Other

* [How to not type your GitHub password every time](https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf)
* [Query Strings in an API file](https://swagger.io/docs/specification/describing-parameters/#query-parameters)

## Questions to discuss
* Where should we check that a playlist exists before adding a playlist entry for it? 
  Lambda function or DAO?

-------------------------------
## A note on the AWS Project ##
-------------------------------
When you are importing the AWS project to Eclipse, there's a specific way you have to
do it, or you may risk breaking the project entirely. Once you've pulled this repository
on the git, go to Eclipse, and under File, select open an existing project from 
your file system. Select the LydiaAWS folder, not the CS3733-Lydia folder. 

If you have the CS3733-Lydia folder open on Eclipse, that should be fine, but whenever 
you edit AWS files, do it from the LydiaAWS folder. Whenever you want to pull or push,
you will need to do that from the CS3733-Lydia folder, and you will not be able to do
this from the LydiaAWS folder. This is a bit of a pain, but the alternatives are to either
have two separate repositories or to change to a new repository altogether every time we
need to delete and remake the AWS project.

## Jobs
The distributions were made in haste, and the work loads of some things may be
more than expected or less than expected. Be ready to take on more work and to
ask where you can help if your parts end up being light, and be willing to ask 
for help if you get stuck, espeically if your task is something on which others 
will depend. 

### Java Objects
DB Java Objects and Lambda functions depend on these, so please have them done soon
If you want guidance on the entity objects, use the fields form the schema.md or
schema.sql files. For guidance on the request or response objects, check the .yaml
file. All of these are in the API folder. If you still have questions, feel free to
ask!
* Make the entity objects [Maggie]
* Make the request objects [Maggie]
* Make the response objects [Maggie]

### Lambda Functions
These are the ones that pretty much anyone should be able to do and that we may need
help on if anyone is able. One of the difficult things is how dependent these are on
the Java objects.
* Get all playlists [Matthew] [And probably everyone]
* Get all segments [Tyler] [And probably everyone] [Theoretically done until Maggie finishes an entity then I'll test]

### Javascript functions
* Get all playlists [Matthew] [Done as I can be until the HTML exists]
* Display the playlists [Matthew] [Done as I can be until the HTML exists]
* Get all segments [Tyler]
* Display the segments [Tyler]

### HTML Jobs
Javascript in part depends on this
* Participant Landing Page [Gracen]
  * List of Video Segments
  * List of playlists (they don't need segments in these yet)
  * How a participant can create a new playlist
  * How a participant can delete an existing playlist
  * How a participant can search for a video segment
  * Video segments are playable
* Admin Landing Page [Gracen]
  * How an admin can register a remote site
  * List of local video segments (presumably repeated or separate 
    from the list of all video segments?)
  * How an admin can mark local segments as remotely available or
    remotely unavailable

### API Jobs
* Upload/debug the API [Matthew]
* Enable CORS on all requests (easy) [Matthew]
* Connect the API to the correct lambda functions [Matthew]

### DB Jobs
Lambda functions in part depend on these
* Make the DB Java Objects [Matthew] [Done for this iteration until the java objects exist]
* Populate the DB with dummy data (easy) [Matthew]
* Screenshot the dummy data to hand in[Matthew]

### Video Jobs
* Trim our segments to 5 seconds [Tyler]
* Convert our segments to Theora Vorbis [Tyler]
* Put the segments in our bucket
* Get the URLs for our segments
  * Probably make them public??
[Canvas post on .ogg's](https://canvas.wpi.edu/courses/16593/discussion_topics/85976)
[Website linked in post](http://www.activovision.com/pogg/doku.php?id=how_to_convert_a_video_to_ogg_with_vlc)