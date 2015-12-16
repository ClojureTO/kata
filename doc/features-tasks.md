# Features

### Wiki
- guidelines for submission process on github (maybe in README.md?)
- guidelines for documenting code
- code review process

### Front End Editor 
- nice ui with highlight (parenedit)
- send code to eval and pprint the result
- ability to print errors
- http://yogthos.net/posts/2015-11-12-ClojureScript-Eval.html

### Voting System
- Ability to vote on problems
	- difficulty
- Ability to vote on solutions
	- Clever answer
	- best pracices

### Problem Submission Editor
- use paragraph blocks
- code blocks
- tests
- * required
- “test your problem” section/button/preview mode
- submit button

### Problems Display
- username
- instructions
- hints?
- tags
- editor
- “run”
- method for determining correct answer (tests)

### Hint System 
- "Have you thought about using <this concept>?"
- provide example on topic that's different from problem trying to solve
- finally, show solution. Maybe "hint" changes to "show solution"

### Categories/Tags
- difficulty level
- Topics

### Points System
- only get points for problem once
- less points the more hints you use
- no points for show solution, can't get points for that problem now
- points for votes on problems/solutions 

### Topic Discussion Page

### Problems Page
- next and back buttons

### Introduction Page
- basic introduction to topic
- include executable and mutable examples (doesn't save them, but allows you to play around with the example code)
- include recommended intro playlist

### Playlists
- allow users to create playlists of problems on certain topics

### Allow Solving Without Login
- doesn't save problems that you solve
- unintrusive reminder to sign in if you want your problem saved/posted

# User standpoint

### users
- submit problems
- submit playlists
- update intros/start new topics
- solve problems - get points for solving
- rate problems
- report problems/posts/users
- pm users
- follow users/topics/playlists
- share on social media sites
- log in via make an account/twitter/facebook/g+/github
- able to solve problems without logging in but not saved in solutions posts/no pts
- user api to see stats, potential for statistical data
- ability to save snippets of code, scratchpad-esque

### moderaters
- all user priviledges
- verify topics/problems
- ban abusive users
- moderate bad language/abusive problems/posts/users

### auto-moderation
- restrictions on new accounts/same email accounts
- word search for abusive words, put into “checklist” for moderators

-

# Tasks! 
- start adding these tasks to issues on github
- Wiki/guidelines
- code editor
- set up heroku app
- connect with github so new versions push to heroku
- hook up to circle to test new code! 
- server side compiling code, send back to front end
- mini problem editor window with a run button in introduction
- markdown problem parsing code blacks out to the editor
- user logins, session management, UUIDs
- database design (how/where to store data)
- output for submitted problems 
	- username
	- instructions
	- hints?
	- tags
	- editor
	- “run”
	- method for determining correct answer (tests)
- submission page/editor
	- use paragraph blocks
	- code blocks
	- tests
	- * required
	- “test your problem” section/button/preview mode
- how to save/post solutions associated with users
	- voting
	- commenting
	- “report”
- introduction page in markdown
- basic problems to start the site off with (continually add as we go?)
	
SHIP IT!


