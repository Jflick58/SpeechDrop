SpeechDrop
==

Fast, efficient, and secure document sharing for debate rounds.

https://img.shields.io/badge/Fly.io-24175B.svg?style=for-the-badge&logo=flydotio&logoColor=white

Screenshots
-- 

![Home Screen](https://i.imgur.com/VxTlwsS.png)
![Document upload and Room view](https://i.imgur.com/2q0zyTc.png)


Running locally
--

1. Clone this repository
2. Run `mvn clean package`
3. Run `java -Dfile.encoding=UTF-8 -jar target/SpeechDrop-1.0-SNAPSHOT.jar -conf config.example.json`
4. Visit `http://localhost:6901/`

Production setup
--

1. Clone and build, as described previously 
2. Copy `config.example.json` to `config.json`
3. Set `debugMediaDownloads` to false
4. Set `mediaUrl` to the root directory of where your static files are served
5. Set `csrfSecret` to a randomly generated string
6. Configure `host` and `port` if needed
7. Set up your preferred webserver to handle proxying, static serving, and socket upgrades (see `nginx` folder for example)
8. Copy JAR to destination directory and switch to it
9. Run `java -Dfile.encoding=UTF-8 -jar SpeechDrop-1.0-SNAPSHOT.jar -conf config.json`
10. Visit site
