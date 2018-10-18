# Spring  Webflux SSEs
[Spring: Web on Reactive Stack](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)

~~~
Flux<ServerSentEvent>, Observable<ServerSentEvent>, or other reactive type:
   * Emit server-sent events. The ServerSentEvent wrapper can be omitted when only data needs to be written 
   (however, text/event-stream must be requested or declared in the mapping through the produces attribute).|
~~~
## Server-Sent Events SSEs
When communicating using SSEs, a server can push data to your app whenever it wants, without the need to make an 
initial request. In other words, updates can be streamed from server to client as they happen. 
SSEs open a single unidirectional channel between server and client.

The main difference between Server-Sent Events and long-polling is that SSEs are handled directly by 
the browser and the user simply has to listen for messages.

## Server-Sent Events vs. WebSockets
Why would you choose Server-Sent Events over WebSockets? Good question.
   
One reason SSEs have been kept in the shadow is because later APIs like WebSockets provide a richer protocol to 
perform bi-directional, full-duplex communication. Having a two-way channel is more attractive for things like games, 
messaging apps, and for cases where you need near real-time updates in both directions. 
However, in some scenarios data doesn't need to be sent from the client. You simply need updates from some server action. 
A few examples would be friends' status updates, stock tickers, news feeds, or other automated data push mechanisms 
(e.g. updating a client-side Web SQL Database or IndexedDB object store). If you'll need to send data to a server, 
XMLHttpRequest is always a friend.
   
SSEs are sent over traditional HTTP. That means they do not require a special protocol or server implementation 
to get working. WebSockets on the other hand, require full-duplex connections and new Web Socket servers 
to handle the protocol. In addition, Server-Sent Events have a variety of features that WebSockets lack by design 
such as automatic reconnection, event IDs, and the ability to send arbitrary events.

## Event Stream Format
Sending an event stream from the source is a matter of constructing a plaintext response, served with a 
**text/event-stream** *Content-Type*, that follows the SSE format. 
In its basic form, the response should contain a ```"data:"``` line, followed by your message, 
followed by two *"\n"* characters to end the stream:
~~~
data: My message\n\n
~~~

## Multiline Data
If your message is longer, you can break it up by using multiple ```"data:"``` lines. 
Two or more consecutive lines beginning with ```"data:"``` will be treated as a single piece of data, 
meaning only one message event will be fired. Each line should end in a single "\n" 
(except for the last, which should end with two). 
The result passed to your message handler is a single string concatenated by newline characters. 
For example:
~~~
data: first line\n
data: second line\n\n
~~~
   
## Associating an ID with an Event
You can send a unique id with an stream event by including a line starting with ```"id:"```:
~~~   
id: 12345\n
data: GOOG\n
data: 556\n\n
~~~   
Setting an ID lets the browser keep track of the last event fired so that if, the connection to the server is dropped, 
a special HTTP header (Last-Event-ID) is set with the new request. This lets the browser determine which event is 
appropriate to fire. The message event contains a e.lastEventId property.

## Controlling the Reconnection-timeout
The browser attempts to reconnect to the source roughly 3 seconds after each connection is closed. You can change that 
timeout by including a line beginning with "retry:", followed by the number of milliseconds to wait before trying to reconnect.

## A Word on Security
From the WHATWG's section on Cross-document messaging security:
~~~   
Authors should check the origin attribute to ensure that messages are only accepted from domains that they expect 
to receive messages from. Otherwise, bugs in the author's message handling code could be exploited by hostile sites.
~~~

### Articles
1. https://www.html5rocks.com/en/tutorials/eventsource/basics/
1. https://www.smashingmagazine.com/2018/02/sse-websockets-data-flow-http2/