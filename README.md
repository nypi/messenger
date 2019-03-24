# Sample messenger for the Java beginners

Practice project for the Croc Java school course.

## Command format

Server accepts commands in a plain text format:

```
COMMAND_ID\n
COMMAND_ARG_0\n
COMMAND_ARG_1\n
...
```

Where `COMMAND_ID` is a number and `COMMAND_ARG_N` is a string.

## Sending messages

To post a new message client should open connection to a server and send a command with ID `0` and two arguments: author usename and message text:

```
0\n
USERNAME\n
MESSAGE_TEXT\n
```

Valid example of a command:

```
0\n
daisy\n
Hey :)\n
```
