<<<<<<< HEAD
# BopBot

BopBot is a customised [JMusicBot](https://github.com/jagrosh/MusicBot) instance for Adrian and friends.

## Build requirements

- [Oracle JDK 8](https://www.oracle.com/au/java/technologies/javase/javase-jdk8-downloads.html) (OpenJDK support not guaranteed)
- Environment variables set (JAVA_HOME=X.Y.Z; PATH, etc. where X.Y.Z is version number)

Verify install with `java -version` - version => 1.8.0

## Compile

1. Clone the modified [JLyrics](https://github.com/aidswidjaja/JLyrics/) dependency

```bash
git clone https://github.com/aidswidjaja/JLyrics.git
```

2. Build the JLyrics dependency

```bash
cd JLyrics
mvn clean package
```

3. Install the JLyrics dependency in your local maven repository - this installs the jar to `~/.m2/repository/com/jagrosh/JLyrics/0.5/JLyrics-0.5.jar`

```bash
mvn install:install-file -Dfile=/path/to/dir/.../JLyrics/target/JLyrics-0.5-jar-with-dependencies.jar -DgroupId=com.jagrosh -DartifactId=JLyrics -Dversion=0.5 -Dpackaging=jar
```

4. Build to jar

```bash
# if pwd == JLyrics then cd out of it
cd bopbot
mvn clean package
```

5. Run jar with arguments

```bash
# if pwd != bopbot then run `cd bopbot`
java -Dnogui=true -jar target/JMusicBot-Snapshot-All.jar
```

## Caveats

- Age-restricted videos on YouTube can't be played right now - waiting for [#649](https://github.com/sedmelluq/lavaplayer/pull/649)
- SoundCloud tracks might sometimes not work - waiting for [#650](https://github.com/sedmelluq/lavaplayer/pull/650)

## Wishlist

- Switching to our own YouTube API key for lavaplayer (which is just used as a maven binary dependency) would be ideal
- Audio effects like 8D audio, bass boost, nightcore
- Enable Twitch streaming status
- Fix help and about section to have more info

## License

```
Copyright 2016-2021 John Grosh (jagrosh).
Copyright 2021 aidswidjaja.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```