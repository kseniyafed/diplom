// <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
//     <head>
//     <script src="webjars/sockjs-client/1.0.2/sockjs.js"></script>
//     <script src="webjars/stomp-websocket/2.3.4/lib/stomp.js"></script>
//     <script src="webjars/jquery/3.1.0/jquery.js"></script>
//     </head>
//
//     <body>
//
//     <button id="connect">Connect</button>
//     <!--<br>-->
//     <button id="ping">Send Ping</button>
// <!--<br>-->
// <button id="disconnect">Disconnect</button>
//
//     <script th:inline="javascript">
// var stompClient = {
//     client: null,
//     socket: null,
//     connect: function () {
//         this.socket = new SockJS('/websocket');
//         this.client = Stomp.over(this.socket);
// //            this.client.debug = null;
//         this.client.connect({}, function (frame) {
//             stompClient.client.subscribe('/topic/pingpong', function (events) {
//                 stompClient.consume(events);
//             });
//         });
//     },
//     consume: function (raw) {
//         console.log(raw);
//     },
//     close: function () {
//         if (this.client != null) {
//             this.client.unsubscribe('/topic/pingpong');
//             this.client.disconnect();
//             this.client = null;
//         }
//     }
// };
//
// $("#ping").click(function() {
//     stompClient.client.send("/app/ping", {}, "");
// })
//
// $("#disconnect").click(function() {
//     stompClient.close();
// });
//
// $("#connect").click(function() {
//     stompClient.connect();
// });
// </script>
// </body>
// </html>