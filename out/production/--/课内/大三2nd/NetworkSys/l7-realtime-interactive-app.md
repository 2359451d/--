# Content

机翻

* 互联网实时应用 Real-time applications and the Internet
  * 什么是实时流量 What is real-time traffic?
  * 为了成功地在**互联网上运行实时应用，有哪些要求和限制** Requirements and constraints
  * **围绕服务质量和用户体验**的一些问题 Quality-of-service and quality-of-experience
* **交互型应用** Interactive applications
  * **会议应用架构** Conferencing application architecture
    * structure of video conferencing system
    * protocol for multi-media video conferencing
  * Signalling, session description, and real-time (multi)media traffic
    * multi media transport
* Streaming applications
  * HTTP adaptive streaming for video on demand

---
* [Content](#content)
* [Outline: 实时流量 Real-time Media Over The Internet](#outline-实时流量-real-time-media-over-the-internet)
  * [互联网实时流量-历史&网络发展的支持：Real-time Traffic on the Internet](#互联网实时流量-历史网络发展的支持real-time-traffic-on-the-internet)
  * [什么是实时流量 & 软/硬实时应用 & 绝对/相对期限：What is Real-time Traffic?](#什么是实时流量--软硬实时应用--绝对相对期限what-is-real-time-traffic)
  * [实时应用不一定需要高性能：Real-time is not necessarily high performance](#实时应用不一定需要高性能real-time-is-not-necessarily-high-performance)
  * [流媒体实时应用的要求：Requirements for Streaming Applications](#流媒体实时应用的要求requirements-for-streaming-applications)
    * [比特率（帧速率）要求&质量，权衡](#比特率帧速率要求质量权衡)
  * [交互式应用时延要求：Requirements for Interactivity](#交互式应用时延要求requirements-for-interactivity)
  * [实时流量必须容错 & 构建中容错问题：Real-time Traffic Must Be Loss Tolerant](#实时流量必须容错--构建中容错问题real-time-traffic-must-be-loss-tolerant)
  * [实时流量传输速率灵活性受限：Real-time Traffic Has Limited Elasticity](#实时流量传输速率灵活性受限real-time-traffic-has-limited-elasticity)
    * [灵活性：实时流量应用 vs TCP应用](#灵活性实时流量应用-vs-tcp应用)
  * [实时应用资源预留（服务质量）：Quality of Service (QoS) Guarantees](#实时应用资源预留服务质量quality-of-service-qos-guarantees)
    * [权衡：资源预留复杂性（成本） & 质量](#权衡资源预留复杂性成本--质量)
  * [用户体验质量：Quality of Experience (QoE)](#用户体验质量quality-of-experience-qoe)
* [Outline: 交互式应用 Interactive Applications](#outline-交互式应用-interactive-applications)
  * [Interactive Conferencing Applications](#interactive-conferencing-applications)
  * [交互式应用-时延 & 比特率（传输速率）要求：Requirements on Timing and Data Rate](#交互式应用-时延--比特率传输速率要求requirements-on-timing-and-data-rate)
  * [语音 vs 视频数据-丢包容错程度：Reliability Requirements](#语音-vs-视频数据-丢包容错程度reliability-requirements)
  * [交互式会议应用-流媒体传输（发送）过程/原理：Interactive Applications: Media Transmission Path](#交互式会议应用-流媒体传输发送过程原理interactive-applications-media-transmission-path)
  * [接收媒体数据包过程（路径）/原理：Interactive Applications: Media Reception Path](#接收媒体数据包过程路径原理interactive-applications-media-reception-path)
  * [传输多媒体的协议栈（标准）：Internet Multimedia Standards](#传输多媒体的协议栈标准internet-multimedia-standards)
  * [媒体传输协议：Media Transport- RTP](#媒体传输协议media-transport--rtp)
  * [RTP报头格式：Header](#rtp报头格式header)
  * [RTP收方-补偿抖动（包间时延）：Media Transport- Timing Recovery](#rtp收方-补偿抖动包间时延media-transport--timing-recovery)
    * [为什么：回放前，添加一点时延？](#为什么回放前添加一点时延)
  * [RTP应用层数据包分组/装帧：Media Transport: Application Level Framing](#rtp应用层数据包分组装帧media-transport-application-level-framing)
  * [向前纠错vs重传：FEC（Forward Error Correction） vs. Retransmission](#向前纠错vs重传fecforward-error-correction-vs-retransmission)
    * [为什么实时应用不使用重传](#为什么实时应用不使用重传)
    * [为什么使用向前纠错](#为什么使用向前纠错)
* [数据信道：Data Channel](#数据信道data-channel)
  * [网页即时通信：Media: WebRTC Data Channel](#网页即时通信media-webrtc-data-channel)
    * [基于SCTP的WebRTC数据信道](#基于sctp的webrtc数据信道)
    * [为什么WebRTC数据信道不基于QUIC](#为什么webrtc数据信道不基于quic)
  * [信令协议 & 会话描述协议（SDP）：Signalling and Session Descriptions](#信令协议--会话描述协议sdpsignalling-and-session-descriptions)
    * [SDP进行呼会话协商：Session Descriptions: SDP Offer/Answer](#sdp进行呼会话协商session-descriptions-sdp-offeranswer)
      * [SDP 会话间媒体协商 缺陷](#sdp-会话间媒体协商-缺陷)
    * [交互会议-SIP协议建立P2P连接：Control of Interactive Conferencing: SIP](#交互会议-sip协议建立p2p连接control-of-interactive-conferencing-sip)
    * [WebRTC基于浏览器会议：Browser-based Conferencing: WebRTC](#webrtc基于浏览器会议browser-based-conferencing-webrtc)
      * [WebRTC目的](#webrtc目的)
  * [交互式应用发展：Future Directions for Interactive Applications](#交互式应用发展future-directions-for-interactive-applications)
* [流媒体视频应用：Streaming Video](#流媒体视频应用streaming-video)
  * [流媒体视频应用-RTP/HTTPS：Streaming Video Applications](#流媒体视频应用-rtphttpsstreaming-video-applications)
  * [CDN内容分布式网络：HTTP Content Distribution Networks](#cdn内容分布式网络http-content-distribution-networks)
  * [HAS流媒体技术原理：HTTP Adaptive Streaming (a.k.a. MPEG DASH)](#has流媒体技术原理http-adaptive-streaming-aka-mpeg-dash)
  * [自适应流媒体协议-DASH: Chunked Media Delivery and Rate Adaptation](#自适应流媒体协议-dash-chunked-media-delivery-and-rate-adaptation)
    * [速率调整](#速率调整)
  * [DASH速率调整 vs TCP拥塞控制：DASH and TCP Congestion Control](#dash速率调整-vs-tcp拥塞控制dash-and-tcp-congestion-control)
  * [DASH时延：DASH Latency](#dash时延dash-latency)
    * [时延源1-TCP重传：Sources of Latency: TCP](#时延源1-tcp重传sources-of-latency-tcp)
    * [时延源2-块大小&视频压缩：Sources of Latency: Chunks and Video Compression](#时延源2-块大小视频压缩sources-of-latency-chunks-and-video-compression)
  * [Future Directions for Streaming Video](#future-directions-for-streaming-video)

# Outline: 实时流量 Real-time Media Over The Internet

互联网上的实时流量。

* 什么是**实时流量**，what is real-time traffic
* 为了成功地在**互联网上运行实时应用，有哪些要求和限制**，what are the requirements and constraints in  order to successfully run real-time traffic,  real-time applications over the Internet
  * 流应用要求 requirements for streaming applications
  * 交互性要求 requirements for interactivity
* 以及**围绕服务质量和用户体验**的一些问题，some  of the issues around quality of service  and user experience
* 以及**如何确保**我们为这些应用的用户**提供良好的体验** how to make  sure we get a good experience for  users of these applications.

## 互联网实时流量-历史&网络发展的支持：Real-time Traffic on the Internet

![](/static/2021-04-13-12-47-24.png)

在互联网上运行实时流量其实已经有很长的历史了 there's actually a long history of  running real-time traffic over the Internet.

* 包括电话和IP语音等应用。它包括互联网广播和流媒体音频应用。它包括视频会议应用，如Zoom。 它包括流媒体电视、流媒体视频应用，如iPlayer和Netflix。 但它也包括游戏、和传感器网络应用，以及各种工业控制系统 And this includes applications like telephony and  voice over IP. It includes Internet radio  and streaming audio applications. It includes video  conferencing applications such as Zoom.  It includes streaming TV, streaming video applications,  such as the iPlayer and Netflix.  But it also includes gaming, and sensor  network applications, and various industrial control systems

而这些实验可以追溯到一个令人惊讶的年代。 最早的RFC关于互联网上实时媒体的主题是RFC741，它可以追溯到20世纪70年代初，描述了网络语音协议。 而这是一次在ARPANET上运行分组语音的尝试，ARPANET是互联网的前身。 而在这一领域的标准制定和实验、研究一直在持续进行。 目前我们用于电话应用、视频会议应用的一套标准，可以追溯到20世纪90年代中期。 它促成了一套协议，如SIP、会话发起协议、会话描述协议、实时传输协议等。  然后又有一个爆发式的发展，大概在2000年中期左右，有了HTTP自适应流媒体，这就导致了MPEG DASH标准等标准，一个应用就是像Netflix和iPlayer。And these experiments go back a surprisingly  long way.  The earliest RFC on the subject of  real-time media on the Internet is RFC741,  which dates back to the early 1970s  and describe the network voice protocol.  And this was an attempt at running  packet voice over the ARPANET, the precursor  to the Internet.  And there’s been a continual thread of  standards developments and experimentation and research in  this area.  The current set of standards, which we  use for telephony applications, for video conferencing  applications, dates back to the mid 1990s.  It led to a set of protocols,  such as SIP, the Session Initiation Protocol,  the Session Description Protocol, the Real-time Transport  Protocol, and so on.   And then there was another burst of  developments, in perhaps the mid-2000s or so,  with HTTP adaptive streaming, and that led  to standards such as the MPEG DASH  standards, an applications is like Netflix and  the iPlayer.

重要的是要认识到，这对网络来说并不是什么新鲜事 what's important, though, is to  realise that this is not new for   the network

* 我们看到世界上每个人都转为使用视频会议，世界上每个人都转为使用Webex，还有Teams，还有Zoom等等。但是这些应用其实已经存在很多年了，这些应用发展起来了，网络也随着这些应用发展起来了，互联网对实时媒体的支持有很长的历史 We've seen everyone in the  world switch to using video conferencing,  and everyone in the world  switch to using Webex, and Teams,  and Zoom, and the like. But these  applications actually existed for many years,   and these applications have developed, and the  network has developed along with these applications,   and there's a long history of support  for real-time media in the Internet

偶尔会听到有人说，互联网不是为实时媒体设计的，我们需要重新架构互联网来支持实时应用，支持未来的多媒体应用 occasionally, hear people saying that  the Internet was not designed for real-time  media, and we need to re-architect the   Internet to support real-time applications,  and to support future multimedia applications

* 不正确的，**互联网从一开始就伴随着多媒体应用的发展和成长** somewhat disingenuous with  history.   The Internet has developed and grown-up with   multimedia applications, right from the beginning
* **虽然它们也许不像某些非实时应用那样流行，但它们一直在持续发展，人们一直在使用这些应用，并构建网络以支持这种类型的流量，至今已有许多年了** And while they've perhaps not been as   popular, as some of the non real-time  applications, they've been a continual strand of   development, and people have been using these   applications and architecting the network to support   this type of traffic, for many,  many years now.
  * i.e. **网络及其传输协议的设计已经发展到支持实时媒体的程度**。 The design of the network and its transport protocols has evolved to support real-time media

## 什么是实时流量 & 软/硬实时应用 & 绝对/相对期限：What is Real-time Traffic?

![](/static/2021-04-13-14-39-23.png)

:orange: 什么叫实时流量、实时应用？What do we mean by real-time traffic, real-time applications?

* **决定性的特征就是流量是有期限的**。the defining characteristic is that the  traffic has deadlines.
* <font color='red'>如果在一定的时间内没有送达数据，系统就会失效</font> The system fails if  the data is not delivered by a  certain time.

:orange: 根据应用程序的类型，**根据实时流量的类型**，可以是**硬（截止期限）实时应用**或**软（截止期限）实时应用** depending on the type of application,  depending on the type of real-time traffic,  those can be what's known as hard  deadlines or soft deadlines.

* 一个**硬截止期限(硬实时应用)**的例子可能是一个控制系统，例如一个信号系统系统，控制信号的数据必须在火车到达信号之前到达，以便适当地改变信号。 an example of a hard deadline might be a control system, such as a railway signalling system, where the data that's controlling the signals has to arrive  at signal before the train does,  in order to change the signal appropriately.
* 在**软实时应用程序**中，<font color="red">为了获得媒体的顺利播放，必须在一定期限内交付数据。</font> where you have to deliver the data  by a certain deadline in order to   get smooth playback of the media.
  * <font color="red">实时多媒体应用程序在软实时应用程序领域中非常重要</font>，Real-time multimedia applications, on the other hand,  very much in the in the realm   of soft real-time applications,
  * 为了得到无故障的音频播放，In order to get a glitch-free playback   of the audio
  * 为了得到流畅的视频播放 in order to get smooth video playback.
    * 这些应用程序往往需要传输数据，也许每50分之一秒传输一次音频，也许每30次每秒，每秒60次，才能得到流畅的视频 And these applications tend to have to  deliver data, perhaps every 50th of a  second for audio, maybe every 30 times   a second, 60 times a second,  to get smooth video.

:orange: <font color="red">没有一个系统在满足其截止期限方面是100%可靠的【错过最后期限的概率是多少? 结果是什么? What is the probability a deadline is missed? What are the consequences?】</font> And it's important to realise that no  system is ever 100% reliable at meeting  its deadlines.

* **设计一个从不错过最后期限的系统是不可能的**。因此，要经常考虑如何安排这些系统，以便在最后期限之前完成一些适当的部分(传输数据)。**这个比例/概率是多少，取决于我们要建立什么样的系统** It's impossible to engineer system that never misses a deadline. So always think about  how can we arrange these systems,   such that some appropriate portion of the  deadline are met.   And what that proportion is, depends on  what system we're building.
* **例子**：
  * 如果这是一个信号系统系统，**我们希望网络不能及时传递信息的概率足够低**，以至于火车不会更有可能出现故障，或者实际的物理信号出现故障，然后网络不能及时传递信息的概率 If it's a railway signalling system,  we want the probability that the network  fails to deliver the message to be  low enough that it's more likely that  the train will fail, or the actual  physical signal will fail, then the probability  of the network failing to deliver the  message in time.
  * 如果是视频会议应用程序或视频流应用程序，风险显然要低得多，**因此可以接受更高的失败概率**。尽管同样取决于应用程序的用途。为一群朋友使用的视频会议系统，只是聊天，显然有不同的可靠性限制，If it's a video conferencing application,  or video streaming application, the risks are   obviously a lot lower, and so you  can accept a higher probability of failure.  Although again, it depends on what the  application’s being used for. A video conferencing   system being used  for a group of friends, just chatting,   obviously has different reliability constraints
    * <font color="red">不同程度的严格限制其最后期限</font>，比如一个用于远程控制无人机，或一个用于远程手术 different degrees  of strictness of its deadlines, than one   being used for remote control of a  drone, or one being used for remote  surgery, for example.

:orange: 而不同的系统可以有不同类型的期限 the different systems can have different  types of deadline

* **数据必须在一定时间之前交付--绝对期限(可能各种类型的数据必须在特定的时间之前交付**) Data must be delivered before a certain time – absolute deadline (It may be that various types of   data have to be delivered before a  certain time)
  * 你必须在火车到达之前把控制信息传递给铁路信号 It may be that various types of   data have to be delivered before a  certain time.  You have to deliver the control information  to the railway signal before the train  gets there. So you've got an absolute  deadline on the data.
* <font color="red">必须在上一个相对截止日期之后的一段时间内定期交付数据</font> Data must be delivered periodically, within some time after the previous – relative deadline
  * 或者说，相对于之前的截止日期，数据必须定期交付 Or it maybe that the data has  to be delivered periodically, relative to the previous deadline.
  * 视频帧必须每30秒或每60秒传送一次。 The video frames have to be delivered every 30th of a second,  or every 60th of a second.

---

:candy: **而不同的应用有不同的约束** different applications have different constraints.

:candy: **不同应用在延迟、绝对期限上有不同的界限** Different bounds on the latency, on the  absolute deadline.

:candy: **不同应用也有相对最后期限的限制 & 对时间的可预测性的限制** But also on the relative  deadline, on the predictability of the timing.

## 实时应用不一定需要高性能：Real-time is not necessarily high performance

:orange: **实时应用不一定需要高性能** Real-time is not necessarily high performance (It’s important to remember that we're not  necessarily talking high performance for these applications.)

* <font color="red">它需要的是可预测的时间，不一定是需要高带宽或低延迟</font> It requires predictable timing, not necessarily high bandwidth or low latency
* 例如，如果我们正在构建一个通过互联网运行的电话系统，我们发送的数据量可能只有每秒几千比特。**但这需要可预见的时机**。If we're building a phone system that   runs over the Internet, for example,  the amount of data we're sending is  probably only a few kilobits per second.   But it requires predictable timing.
  * 这个包含语音数据的**数据包必须以至少大致可预测的、大致相等的间隔发送**，以便我们能够纠正时间并顺利地播放语音 This packets containing the speech data have to be delivered with at least approximately predictable, approximately equal,  spacing, in order that we can correct   the timing and play out the speech  smoothly.

## 流媒体实时应用的要求：Requirements for Streaming Applications

![](/static/2021-04-13-14-52-44.png)

:orange: **视频点播应用来说，在大多数情况下，其实并没有什么绝对的期限** Video on demand has no absolute deadline, but needs regular data to prevent stalls once started

* 比如Netflix或YouTube或iPlayer，如果你在看**一部电影**，在你点击播放按钮后，如果需要5秒、10秒、20秒才能开始播放也没关系， If you're watching a movie, it's okay  if it takes 5, 10, 20 seconds  to start playing, after you click the  play button
  * **前提是播放开始后就能顺利播放** provided the playback is smooth once it  has started.
* 也许如果是一个短的东西，也许是一个只有**几分钟的YouTube视频，那么你希望它能更快地开始**。但同样，它也不必在你按下播放键的几毫秒内开始。And maybe if it's a short-thing,  maybe it's a YouTube video that's only  a couple of minutes, then you want  it to start quicker.   But again, it doesn't have to start  within milliseconds of you pressing the play  button
  * 一两秒的延迟是可以接受的，前提是播放开始后很流畅。A second or two of latency  is acceptable, provided the playback is smooth  once it starts.

:orange: **直播视频可能有绝对期限** Live video may have absolute deadlines

* 显然，如果你正在 YouTube 或 iPlayer 上观看现场体育赛事，**你肯定不希望它与电视转播的赛事相差太远**。Clearly if you're watching  a live sporting event on YouTube or  on the iPlayer, for example, you don't  want it to be too far behind  the same event being watched on  broadcast TV
* <font color="deeppink">但是，对于这些应用程序来说，通常是**相对期限**，以应用程序启动后的顺利回放，而不是绝对期限</font> But, for these applications,  typically it's the relative deadlines, and smooth  playback once the application has started,  rather than the absolute deadline that matters.

---

### 比特率（帧速率）要求&质量，权衡

![](/static/2021-04-13-15-02-26.png)

:orange: **流媒体应用每秒所需的比特数在很大程度上取决于质量**。The amount of bits per second it  needs depends to a large extent on  the quality.

* 显然，**质量越高越好，比特率越高越好（流畅度**） obviously, higher quality is better,  a higher bit rate is better.
* <font color="green">在某种程度上，是有限度的。这个限制取决于相机，相机的分辨率，相机的帧率，以及显示屏的大小，等等</font> But, to some extent, there's a limit  on this. And it's a limit depending  on the camera, on the resolution of  the camera, and the frame rate of  the camera, and the size of the  display, and so on.
  * 而且你不一定需要几十或几百兆比特率。你可以在每秒个位数的兆位上得到非常好的视频质量。And you don't necessarily need many tens  or hundreds of megabits. You can get  very good quality video on single digit  numbers of megabits per second.
    * 即使是制作质量，工作室质量，也不过是每秒几百兆比特。And even  production quality, studio quality, is only hundreds  of megabits per second
  * <font color="red">所以，这些应用通常可以发送的速率是有上限的，当你达到采集设备的极限时，你就达到了显示设备的极限</font> So there’s an  upper bound on that the rate at  which these applications  can typically send, when you hit the   limits of the capture device, you hit  the limits of the display device.
* 而且，很多时候，对于这些应用来说，**可预测性比绝对质量更重要（数据包必须以至少大致可预测的、大致相等的间隔发送，以便我们能够纠正时间并顺利地播放）**。And, quite often, for a lot of  these applications, predictability matters more than absolute  quality.
  * 往往一部电影，它的质量是稳定的，比起一部偶尔质量很好，但一直降到较低分辨率的电影，更不令人讨厌。所以，可预见性往往才是最关键的。It's often the less annoying to have  a movie, which is a consistent quality,  than a movie which is occasionally very  good quality, but keeps dropping down to  a lower resolution. So predictability is often  what's critical.

:orange:**对于给定的比特速率，您还要在帧速率和质量之间权衡**。for a given bit rate,  you're also trade off between frame rate and quality

* 流畅度，还是非常精细的细节（分辨率）？而且， Do you want smooth motion,  or do you want very fine detail?
  * 如果你想要平滑的运动和精细的细节，你必须提高速率。if you want both smooth motion  and fine detail, you have to increase  the rate
  * 但是您可以在它们之间权衡，在给定的比特率下，在不同的质量级别上 But you can trade-off between  them, at a given bit rate,  a different quality level.

## 交互式应用时延要求：Requirements for Interactivity

![](/static/2021-04-13-15-16-01.png)

对于交互式应用来说，要求有些不同。**它们在很大程度上取决于人类的感知，以及能够顺利对话的要求** For interactive applications, the requirements are a  bit different. They depend very much on  human perception, and the requirements to be  able to have a smooth conversation.

* 对于**电话，对于视频会议的应用**，人们对这种事情的研究已经有一段时间了 For phone calls, for video conferencing applications,  people have been doing studies of this  sort of thing for quite a while.
  * 你听到的典型的界限是**单向的嘴对耳延迟**，所以从我说话到它通过空气到麦克风，被捕获，压缩，通过网络传输，解压缩，播放，**从扬声器到你的耳朵**，**延迟不应该超过150毫秒。而且，如果超过这个数字，对话就会变得有点尴尬。人们开始互相讨论，这使得谈话变得有点困难**。  The typical bounds you hear expressed are  **one-way mouth-to-ear delay**, so the delay from  me talking, to it going  through the air to the microphone,  being captured, compressed, transmitted over the network,  decompressed, played-out, back from the speakers to  your ear,  should be no more than about 150  milliseconds. And, if it gets more than  that, it starts getting a bit awkward  for the conversations. People start talking over  each other, and it gets to be  a bit difficult for a conversation.
    * 国际电信联盟-电信联盟的 g. 114号建议详细地谈到了这一点，以及其中存在的限制。And the ITU-T Recommendation G.114 talks about  this, and about the constraints there,  in a lot of detail.
  * **需要同步音频画面的视频会议** Video conferences want to lip-sync audio and video
    * 如果音频提前了大约15毫秒，或者比视频慢了大约45毫秒。人们似乎**更经常注意到音频在视频前面**，而不是在视频后面。Video conferences in terms of lip sync,  people start noticing if the audio is  more than about **15 milliseconds ahead,  or more than about 45 milliseconds behind  the video**. And it seems that people  notice more often if the audio is  ahead of the video, than if it's  behind the video.
      * <font color="red">这为整个网络的总体延迟以及音频和视频流之间的延迟变化提供了相当严格的界限</font> So this gives quite strict bounds for  overall latency across the network, and for  the variation in latency between audio and  video streams.

![](/static/2021-04-13-17-22-56.png)

:candy: 如果你正在进行一个交互式的对话，那么这种对话的界限要比更像**演讲**风格的对话更为严格，If you're having an interactive conversation,  the bounds of tighter than if it's  more of a lecture style

* 因为这种**讲座大多是单向的**，有更多的结构化停顿和更有条理的提问。这种类型的应用程序**可以忍受更高的延迟** where it's  mostly unidirectional, with more structured pauses and   more structured questioning. That type of application  can tolerate higher latency.

:orange: 分布式的**音乐表演**，那么你需要更低、更低的延迟(tight bounds) distributed music performance,   then you need much lower,  much lower latency.

* 如果你想象一个管弦乐队，你测量管弦乐队的规模，你想象一下音速，你会得到**15毫秒**，声音从管弦乐队的一边传到另一边。And, if you think about something like  an orchestra, and you measure the size  of the orchestra, and you think about  the speed of sound, you get about  15 milliseconds for the sound to go  from one side of the orchestra to  another.
* 所以，这种延迟水平显然是可以接受的，**但是一旦超过20或30毫秒**，人们就很难以同步的方式收听了。So, that sort of level of latency   is clearly acceptable, but once it gets  more than 20 or 30 milliseconds,  it gets very difficult for people to  play in a synchronised way
  * 如果你曾经尝试过通过 Zoom 呼叫播放音乐，你会发现它根本不起作用，因为延迟太高了，如果你想在视频会议上协同播放音乐 if you’ve ever  tried to play music over a Zoom  call, you'll realise it just doesn't work,   because the latency is too high for  that,  If you're trying to  play music collaboratively on a video conference.

## 实时流量必须容错 & 构建中容错问题：Real-time Traffic Must Be Loss Tolerant

![](/static/2021-04-13-17-41-31.png)

网络是非常尽力的网络，。the network is  very much a best effort network

* 它并**不能保证时效** it doesn't guarantee the timing.
  * 数据在网络中穿行的时延量，**很大程度上取决于路径的传播时延，和排队量，以及所走的路径**，**它根本无法预测**。The amount of latency for data to  traverse the network very much depends on the propagation delay of the path,  and the amount of queuing, and on  the path taken, and it's not predictable  at all.
* **数据包可能会丢失--重传需要时间，而且可能无法在期限前送达。** Packets can be lost – retransmissions take time, and may not arrive before the deadline
  * 图示：它显示的是一个特定路径的往返时间的变化，我们可以看到大多数数据包都捆绑在一起，**并且有一个相当一致的界限**，但是偶尔会出现峰值，因为数据包需要更长的时间才能到达 If we look at the figure on  the left, here, it's showing the variation  in round trip time for a particular  path. And we see that most of  it is bundled up, and there’s a  fairly consistent bound, but there are occasional  spikes where the packets take a much  longer time to arrive.
* 而在一些网络中，这**些影响可能相当大，它们可能需要相当长的时间才能送达数据** And in some networks these effects can  be quite significant, they can take quite  a long time for data to arrive.

:candy: **所有这些的后果，就是实时应用需要具有容错性**。The consequence of all this, is that  real-time application needs to be loss tolerant.

* 如果你要构建一个可靠的应用，它就必须**重传数据，而这些数据可能及时到达，也可能不及时到达**。 If you're building an application to be  reliable, it has to retransmit data,  and that may or may not arrive  in time.
  * 所以你要把它构建成不可靠的应用，而不是一定要重传数据 So you want to build  it to be unreliable, and not to  necessarily retransmit the data.
  * 您还希望它能够处理某些数据包可能被延迟的情况，并且能够在这些数据包到达太晚的情况下继续处理。You also want it to be able  to cope with the fact that some  packets may be delayed, and be able  to proceed even if those packets arrive  too late.
* 所以应用需要能够弥补，容忍，损失，不管是不可达的数据，还是延误的数据。So it needs to be able to  compensate for, to tolerate, loss, whether that's  just data which is never going to  arrive, or data that's just going to  arrive late.
  * 而且，很明显，在质量下降之前，你可以隐藏多少损失，你可以忍受多少损失，这是有一个界限的 And, obviously, there's a bound on how  much loss you can conceal, how much  loss you can tolerate before the quality  goes down.

:candy: 构建实时应用的挑战/问题the challenge in building these applications  is to

* 在一定程度上，对网络进行工程设计，**使其不会丢失许多数据包，比如丢失率、时间变化，这些都足够低，以至于应用程序能够正常工作**。 partially, engineer the network such that it  doesn't lose many packets, such the loss  rate, the timing variation, is low enough  that the application is going to work.
* **在构建应用程序时能够容忍丢包，能够隐藏丢失数据包的影响** But, also, it’s in building the application  to be tolerant to the loss,  in being able to conceal the effects  of lost packets.

## 实时流量传输速率灵活性受限：Real-time Traffic Has Limited Elasticity

流量的实时性也影响了拥塞控制的工作方式，它影响了数据在网络中的传输方式。The real-time nature of the traffic also  affects the way congestion control works,  it affects the way data is delivered  across the network.

![](/static/2021-04-13-17-50-43.png)

:orange: **TCP拥塞控制**，拥塞控制会使传输速度与网络上的可用容量相匹配 TCP congestion control,  congestion control adapts the speed of transmission  to match the available capacity over the  network.

* 如果网络容量大了，它的发送速度就快。如果网络超载，它变慢。If the network has more capacity,  it sends faster. If the network gets  overloaded, it sends slower.
* **而且传输的速度是有弹性的**。如果你要下载一个网页，如果你要下载一个大的文件，速度越快越好，**但是拥塞控制能选什么速率并不重要**。 And the transfers are elastic. If you're  downloading a web page, if you're downloading  a large file, faster is better,  but it doesn't really matter what rate  the congestion control can pick.
  * 你希望它能以最快的速度降下来，应用程序也能适应 You want  it to come down as fast as  it can, and the application can adapt.
  * 不管选择什么速率，应用程序都可以调整至某速率 The application can adapt to any chosen rate

:orange: **实时流量**的弹性要小得多 Real-time traffic is much less elastic

* 它**有一个最低速率**，有一定的质量水平，一定的比特率，**低于这个水平的媒体是无法使用的**【媒体有一个最低速率，低于这个速率就不能使用 Media has a minimum rate, below which it cannot be used】 It’s got a minimum rate, there’s a  certain quality level, a certain bit rate,  below which the media is just unintelligible.
  * 如果你在传输语音，你需要一定数量的千比特每秒。否则，出来的就只是莫名其妙的语言。 If you're transmitting speech, you need a  certain number of kilobits per second.  Otherwise, what comes out is just not  intelligible speech.
  * 如果你发送视频，你需要一定的比特率，否则你不能获得完整的动态视频; 质量太低，帧速率太低，而且它不是长视频 If you're sending video, you need a  certain bit rate, otherwise you can't get  full motion video over it; the quality  is just too low, the frame rate  is just too low, and it's ino  longer video.
  * <font color="red">如果拥塞控制算法不能维持这个速率--如果网络容量不足--就不能使用实时流量。</font> If the congestion control algorithm cannot sustain this rate – if the network has insufficient capacity – real-time traffic cannot be used
* 同样，**实时流量应用有最大速率** Media has a maximum rate → cannot consume more
  * 【**音频受采集采样率限制** Audio limited by capture sampling rate】如果你发送语音数据，如果你发送音频，这取决于捕获率，也就是采样率。而且，即使是最高质量的音频，你可能不会看到超过一兆比特，几兆比特，对于 CD 质量，环绕立体声，媒体。If you're sending speech data, if you're  sending music, it depends on the capture  rate, the sampling rate.  And, even for the highest quality  audio, you're probably not looking at more  than a megabit, a couple of megabits,  for CD quality, surround sound, media.
  * 【**视频受限于捕获帧速率和分辨率** Video limited by capture frame rate and resolution】同样，对于视频，它取决于摄像机的类型，帧率，分辨率，等等。再说一次，一小部分兆比特，几十兆比特，在最极端的情况下是几百兆比特，你可以得到一个发送速率的上限。  And again, for video, it depends on  the type of camera, the frame rates,  the resolution, and so on. Again,  a small number of megabits, tens of  megabits, in the most extreme cases hundreds  of megabits, and you get an upper  bound on the sending rate.

### 灵活性：实时流量应用 vs TCP应用

:orange: <font color="red">所以，实时应用不能使用无限量的流量，不像TCP。它们受制于捕获媒体的速率。 但同时，它们也不能任意地慢，这就影响了我们发送这些(实时流量)数据的方式，因为我们在这些（实时）应用的发送速率上的灵活性较差</font>。So, real-time applications can't use infinite amounts  of traffic,  unlike TCP. They're constrained by the rate  at which the media is captured.  But also, they can't go arbitrarily slowly,  This affects the way we have to  send that data, because we have less   flexibility in the rate at which these  applications can send.

## 实时应用资源预留（服务质量）：Quality of Service (QoS) Guarantees

![](/static/2021-04-13-21-03-04.png)

需要考虑在多大程度上可以或者说应该**为这些实时应用（流量）保留网络容量(带宽，资源预留)** need to think to what  extent it's possible, or desirable, to reserve capacity  for these applications.

* 可以设计一个网络，比如它可以**保证一定数量的数据是可用**的。这样它就保证一个每秒5兆比特的信道可以用来传输视频 There are certainly ways one can engineer  a network, such that it guarantees that  a certain amount of data is available.  Such that it guarantees that, for example,  a five megabit per second  channel is available to deliver video.
* <font color="red">如果实时应用程序是非常关键的应用，为其保留一定网络容量（带宽）是有意义的。</font> if the application is very critical,  maybe that makes sense.
  * 如果你正在做远程手术，你可能需要保证视频的容量。If you're doing remote surgery, you probably  do want to guarantee the capacity is  there for the video
  * **但是，对于许多应用程序来说，并不清楚它是否需要**。But, for a lot of applications,  it's not clear it’s needed
    * 所以我们有一些协议，比如资源预留协议协议，RSVP 协议，比如协调链路层网络的多协议标签交换协议，比如在5 g 网络中网络切片的想法，所以我们可以设置**资源预留** we have protocols, such as the  Resource Reservation Protocol, RSVP,  such as the Multi-Protocol Label Switching protocol  for orchestrating link-layer networks, such as the  idea of network slicing in 5G networks,  so we can set up resource reservations.

:orange: **但是资源预留会增加复杂性** This adds complexity.

* **需要流来发送信号，建立授权，计费**
  * 它增加了信号。您需要以某种方式向网络发出信号，**表示需要设置此预留，并告诉它实时流量需要什么资源**  It adds signalling.  You need to somehow signal to the  network that you need to set up  this reservation, tell it what resources the  traffic requires
  * **需要证明发送方被允许保留这些资源，并能为保留的容量付费--资源预留会阻止其他用户访问网络，所以有一定的成本** Need to demonstrate that sender is allowed to reserve these resources and can pay for the reserved capacity – reservations prevent other users from accessing the network, so have some cost

![](/static/2021-04-13-21-03-30.png)

:orange: **如果网络有容量处理流量，那么资源预留就不起作用** If the network has capacity for the traffic, reservations don’t help

* <font color="red">如果运营商设计的网络有足够的容量来处理所有的流量，那么预留就没有用了</font> If the operators designed the network so  it has enough capacity for all the  traffic it's delivering, the reservation doesn't help.

:orange: **如果网络没有足够容量处理实时流量，则允许运营商区别对待愿意付费的客户**。 If the network does not have capacity, they allow the operator to discriminate in favour of customers who are willing to pay

* 只有当网络没有容量时，预订才有帮助。这是一种允许运营商在没有投入足够网络资源的情况下，区别对待那些愿意支付额外费用的客户的方式 The reservations only help when the network  doesn't have the capacity.  They’re a way of allowing the operator,  who hasn't invested in sufficient network resources,  to discriminate in favor of the customers  who are willing to pay extra.
* 区别对待，这样那些愿意付钱的顾客可以得到高质量的产品，而那些不付额外费用的顾客只能得到一个运行没那么好的系统  To discriminate so that those customers who  are willing to pay can get good  quality, whereas those who don't pay extra,  just get a system which doesn't work  well.
* **域间的资源预留和计费可能难以商定和实施**。Inter-domain reservations and accounting can be difficult to agree and implement

### 权衡：资源预留复杂性（成本） & 质量

:candy: 因此，资源预留是否一定会增加效益还不清楚。当然，它们也有应用的地方 So, it’s not clear that resource reservations  necessarily add benefit.  There are certainly applications where they do.

* 但是，对于许多应用程序来说，对比保留资源以获得质量保证的成本，构建会计系统的成本，构建资源预留系统的复杂性，**购买更多的容量往往更容易，更便宜，这样一切都能正常工作，没有必要预留** But, for many applications, the cost of  reserving the resources to get guaranteed quality,  the cost of building the accounting system,  the complexity of building the resource reservation  system, it's often easier, and cheaper,  just to buy more capacity, such that  everything works and there's no need for  reservations.
  * **权衡**：不清楚，我们是否需要一个网络来提供这些保证，但是需要收费，认证，授权，以及知道谁在发送什么流量，这样你就可以知道他们是否为适当的质量支付了费用。或者，是否只有保证每个人发送容量，我们只是架构网络，使其对大多数事情足够好，**并接受偶尔的质量失误** It’s a different model. It's not clear,  to me, whether we want a network  that provides those guarantees,  but requires charging, and authentication, and authorization,  and knowing who's sending what traffic,  so you can tell if they've paid  for the appropriate quality.  Or, whether it's better just for everyone  to be sending, and we just architect  the networks so that it's good enough  for most things, and accept occasional quality  lapses.

## 用户体验质量：Quality of Experience (QoE)

:orange: 用户体验质量 What ultimately matters is subjective quality of experience

* 应用是否真正满足了用户的需求？Does the application meet user needs?
* 它是否能让他们进行有效的沟通？Does the application allow users to communicate effectively?
* 它是否提供了引人注目的娱乐？Does it provide compelling entertainment?
* 它是否提供了足够好的视频质量？Does  it provide good enough video quality?

:orange: 这不是单纯一个维度的衡量标准 It’s very much not a one dimensional  metric

* **你在评估用户体验的哪一方面**？ What aspect of user experience are you evaluating?
  * 当你问用户“音质听起来怎样?”你会对音乐的质量，或者演讲的质量有不同的看法，而不是问“你能听懂吗?”你问的问题很重要 When you ask the user  “Does it sound good?”, you get a  different  view on the quality of the music,  or the quality of the speech,  than if you ask “can you understand  it?”  The question you ask matters
* **这取决于人们正在做的工作。你的衡量标准与正在执行的任务有什么关系**？it depends on the task  people are doing. How does your metric relate to the task being performed?
  * 例如，人们需要远程手术的质量与人们需要远程讲座的质量不同 The quality people need  for remote surgery is different to the  quality people need for a remote lecture,  for example.  
* **用户体验的某些方面可以通过技术指标来估计。（这种用户体验的某些方面，您可以通过查看技术指标(如数据包丢失和延迟)来进行评估）**Some aspects of user experience can be estimated from technical metrics （And some aspects of this user experience  you can estimate from looking at technical  metrics such as packet loss and latency.）
  * 国际电信联盟有一个叫做“电子模型”的东西，这是一个很好的主观测量语音质量的方法，基于对延迟，时间变化，以及语音数据丢包的观察 And the ITU has something called the  E-model, which is a really good subjective  measure of speech quality, based on looking  at the latency, and the timing variation,  and the packet loss of speech data.
* 但是，特别是当你**开始谈论视频，特别是当你开始谈论特定的应用时，它往往是非常主观的，非常依赖任务（有些方面是主观的，取决于任务--需要用户试验。）** But, especially when you start talking about  video, and especially when you start talking  about  particular applications, it's often very subjective,  and very task dependent.（Some aspects are subjective and task dependent – need user trials）
  * 你需要真正地建立这个系统，尝试一下，然后问人们“那么它工作得怎么样?”“听起来不错吧?”“你能理解吗?”“你喜欢吗?”您需要进行用户试用，以了解用户体验的质量。    And you need  to actually build the system, try it  out, and ask people “So how well  did it work?”  “Does it sound good?” “Can you understand  it?” “Did you like it?” You need  to do user trials to understand the  quality of the experience of the users.

# Outline: 交互式应用 Interactive Applications

* Structure of video conferencing systems
* protocols for multi-media video conferencing
* multi-media transport

## Interactive Conferencing Applications

交互式会议应用是什么?what do we mean by interactive  conferencing applications

* 电话等应用，比如说IP语音，还有比如说视频会议。 这些应用比如说大学的电话系统，比如说Skype，比如说Zoom或者是Webex或者是微软的Teams Well I'm talking about applications such as  telephony, such as voice over IP,  and such as video conferencing.  These are applications like the university's telephone  system, like Skype, like Zoom or Webex  or Microsoft teams
  * Telephony
  * Voice-over-IP (VoIP) 
  * Video conferencing
* 而这个领域其实在互联网界发展的时间已经出奇的长 And this is an area which has  actually been developing in the Internet community  for a surprisingly long amount of time.
  * 早期的标准，这里的早期工作，可以追溯到20世纪70年代早期。 而关于这个主题的第一个互联网RFC，即网络语音协议，实际上是在1976年发表的 As we discussed in the first part  of the lecture, the early standards,  the early work here, date back to  the early 1970s.  And the first Internet RFC on this  subject, the Network Voice Protocol, was actually  published in 1976
  * 我们今天用于视频会议应用、用于电话、用于IP语音的标准，可以追溯到最初的90年代初和中期 The standards we use  today for video conferencing applications, for telephony,  for voice over IP, date from the  early- and mid-1990s initially.
    * 当时有一套应用，比如说CE-SeeMe,幻灯片右下角看到的是一套叫做Mbone会议工具的应用,它是很多这些标准协议的原型 There were a set of applications,  such as CE-SeeMe, which you see at  the bottom right at the slide here,  a set of applications called the Mbone  conferencing tools, and the picture on the  top right of the slide is an  application I was involved in developing in  the late 1990s in this space,  which prototyped a lot of these standard  protocols.
    * **它们促成了一系列标准的发展，比如会话描述协议（SDP）、会话启动协议（SIP）和实时传输协议（RTP），这些标准构成了这些现代视频会议应用的基础**。They led to the development of  a set of standards, such as the  Session Description Protocol, SDP, the Session Initiation  Protocol, SIP, and the Real-time Transport Protocol,  RTP, which formed the basis of these  modern video conferencing applications.
  * 这些方法被广泛采用。国际电信联盟将它们作为 h. 323系列视频会议系统建议的基础。许多商业电话产品都是用它们制造的。第三代合作伙伴计划3GPP 采用了这些标准作为现行移动电话标准的基础。These got pretty widely adopted. The ITU  adopted them as the basis for it  H.323 series of recommendations for video conferencing  systems.  A lot of commercial telephony products are  built using them. And the Third Generation  Partnership Project, 3GPP, adopted them as the  basis for the current set of mobile  telephone standards.
    * 所以，如果你打一个电话，一个手机电话，你就是在使用这些标准的后继。So, if you make a  phone call, a mobile phone call,  you’re using the descendants of these standards.
  * 而且，最近，基于 WebRTC 浏览器的会议系统再次将这些协议整合到浏览器中，基于 SDP 和 RTP 协议，还有一套相同的会议标准，就是你在幻灯片右边看到的工具的原型 And also, more recently, the WebRTC browser-based  conferencing system again incorporated these protocols into  the browser, building on SDP, and RTP,  and the same set of conferencing standards  which were prototyped in the tools you  see on the right of the slide.

## 交互式应用-时延 & 比特率（传输速率）要求：Requirements on Timing and Data Rate

recap - 交互式应用要求(延迟)

![](/static/2021-04-13-22-41-31.png)

* <font color="red">如果你正在构建交互式会议应用，你对延迟有相当严格的限制</font>if you're building interactive  conferencing applications, you've got fairly tight bounds  on latency.
  * 如果你想要一个合理的**交互式对话**（应用），**从嘴到耳朵的单向延迟**，必须**不超过150毫秒左右** The one-way delay, from mouth to ear,  if you want a sensible interactive conversation,  has to be no more than somewhere  around 150 milliseconds.
    * 单向口对耳延时，最大150ms，用于电话。One-way mouth-to-ear delay ~150ms maximum for telephony
  * 如果你正在建立一个**视频会议**，你希望音频和视频之间的唇语同步合理紧凑，**音频不超过约15毫秒领先于视频，不超过约45毫秒落后**。And if you're building a video conference,  you want reasonably tight lip sync between  the audio and video,  with the audio no more than around  15 milliseconds ahead of the video,  and no more than about 45 milliseconds  behind.

:orange: User experience degrades gracefully

* <font color="red">界限，150毫秒的端到端延迟; 提前15毫秒，落后45毫秒，对嘴型同步，不是严格的界限</font> The bounds, 150 milliseconds end-to-end latency;  the 15 milliseconds ahead, 45 milliseconds behind,  for lip sync, are not strict bounds.
  * 越短越好，但是如果延迟，如果偏移，超过这些值，它就会逐渐变得越来越不可用 Shorter is better, but  If the latency, if the offset,  exceeds those values, it gets to gradually  become less-and-less usable,
  * 人们开始注意到音画同步问题，但没有什么是灾难性的问题 people start talking over  each other, people start noticing the  that the lack of lip-sync, but nothing  fails catastrophically. 
  * 我们正在寻找的值: 150毫秒范围内的端到端延迟，以及在几毫秒内同步到10毫秒的有声视频。But that's the sort of  values we're looking at: end-to-end delay in  the hundred 150 millisecond range, and audio-video  synchronized to within a few 10s of  milliseconds.

---

:orange: 我们发送的**数据速率**很大程度上**取决于发送的媒体类型，以及x使用的编码译码器和压缩方案**。The data rates we’re sending depend,  very much, on what type of media  you're sending, and what codec, what compression  scheme you use.

* 对于**发送语音**来说，语音压缩通常需要大约20毫秒的语音数据，持续时间大约是1/50秒，每20毫秒，每1/50秒，它就会抓取接收到的下一段音频，压缩它，然后通过网络传输。然后在接收端解码，解压，并在同样的时间段内完成  For sending speech, the speech compression typically  takes portions of speech data that are  around 20 milliseconds in duration, about 1/50th  of a second in duration, and every  20 milliseconds, every 1/50th second, it grabs  the  next chunk of audio that's been received,  compresses it, and transmits it across the  network.  And this is decoded at the receiver,  decompressed, and played out on the same  sort of timeframe.
  * **数据速率取决于所需的质量级别**。发送语音数据的速度可以达到每秒10到15千比特，**虽然它的传输质量通常比较高，也许有【几百千比特】，这样就可以得到听起来很舒服的【高质量语音】，但是如果需要的话，它可以达到非常低的比特率**。The data rates depends on the quality  level you want. It's possible to send  speech with something on the order of  10-15 kilobits per second of speech data,  although it's typically sent at a some  somewhat higher quality, maybe a couple of  hundred kilobits, to get high quality speech  that sounds pleasant, but it can go  to very low  bit rates if necessary.
  * 基于目前的情况，很多这样的应用程序的（语音）质量都有点低。And a lot of these applications very  the quality a little, based on what's  going on.
    * 当人们在说话的时候，他们编码的质量更高，They encode higher quality when  it's clear that the person is talking
    * 当有背景噪声的时候，发送数据包的频率更低，他们编码的比特率更低。they send packets less often,  and encoded with lower bit rates,  when it's clear there's background noise
    * 如果你发送的是**高质量**的音乐，你需要比发送语音每秒**更多**的比特 If you're sending good quality music,  you need more bits per second than  if you're sending speech.
* **对于视频，帧率，分辨率，在很大程度上取决于相机，取决于处理器压缩的时间，取决于你是否有硬件加速的视频压缩** For video, the frame rates, the resolution,  very much depend on the camera,  on the amount of processor time you  have available to do the compression,  whether you've got hardware accelerated video compression  or not.
  * 在视频压缩算法中，你正在使用的视频编码译码器。**帧速率在25到60帧率之间是很常见的** And on the video compression  algorithm, the video codec you're using.  Frame rates somewhere in the order of  25 to 60 frames per second are  common
  * 视频分辨率从邮票大小到全屏、高清或4 k 视频不等。你可以用像 h. 264这样的编码译码器获得高质量的视频，每秒大约2到4兆比特 Video resolution varies from postage stamp sized,  up to full screen, HD, or 4k  video.  You can get good quality video with  codecs like H.264, at around the two  to four megabits per second range.
  * 很明显，如果你要进行全动态、4k 的电影编码，你需要更高的速率。**但是，即使这样，你可能也不会看到超过4兆、8兆或10兆每秒的速度** Obviously, if you're going up to  full-motion, 4k, movie encoding, you'll need higher  rates than that. But, even then,  you’re probably not looking at more than  four, eight, ten megabits per second.

:candy: <font color="red">.因此，您可以看到**这些应用程序具有合理的延迟界限，以及合理的高，但不过高的位速率界限**。2到4兆比特，甚至8兆比特，通常在大多数住宅，家庭网络，连接是可以实现的。**而且150毫秒的端到端延迟通常是可以实现的，没有太多的困难**，只要你不试图去跨大西洋或跨太平洋。</font> So, what you see is that these  applications have reasonably demanding latency bounds,  and reasonably high, but not excessively high,  bit-rate bounds. Two to four megabits,  even eight megabits, is generally achievable on  most residential, home  network, connections. And 150 milliseconds end-to-end latency  is generally achievable without too much difficulty  as long as you're not trying to  go transatlantic or transpacific.

## 语音 vs 视频数据-丢包容错程度：Reliability Requirements

![](/static/2021-04-14-13-28-56.png)

:orange: 在可靠性要求方面，<font color="red">语音数据具有较高的容错率</font> In terms of reliability requirements，Speech data is highly loss tolerant

* 丢失隐藏(**丢包补偿机制**)可以隐藏（补偿）10-20%的随机数据包丢失，而不会有明显的质量损失 Loss concealment can hide 10-20% random packet loss without noticeable loss in quality
  * 而且，随着的增加【向前纠错机制】，加上【错误纠正代码】，可以建立一个系统来处理可能50% 的丢失数据包 And, with the addition of forward error  correction, with error correcting codes, it’s quite  possible to build systems that work with  maybe 50% of the packets being lost.
* **数据包的突发丢失**更**难以隐藏（补偿**），而且往往会导致语音播放中听不到的小故障，但在网络中这种情况**相对少见** Bursts of packet loss are harder to  conceal, and tend to result in inaudible  glitches in the speech playback, but they're  relatively uncommon in the network.

:orange: <font color="red">视频数据包丢失有些难以补偿。</font> Video packet loss is somewhat harder to  conceal.

* 对于**流式视频应用程序**，如果你正在发送一部电影，例如，你可以依靠偶尔的场景改变来**重置解码器状态**，并从任何损失的影响中恢复 With streaming video applications, if you're sending  a movie, for example, you can rely  on that the occasional scene changes to  reset the decoder state, and to recover  from the effects of any loss.
* **视频会议**，通常不会改变场景，所以你必须**进行滚动修复，滚动重传，或者使用某种形式的【前向错误更正】来检测损失** With video conferencing, there aren’t typically scene  changes, so you have to do a  rolling repair,  a rolling retransmission, or some form of  forward error correction to detect the losses.
* <font color="deeppink">所以视频比音频对数据包丢失更敏感。不过，同样地，人们对视频质量的破坏并不像对音频质量的破坏那么敏感</font> So video tends to be more sensitive  to packet loss than the audio.  Equally, though, people are less sensitive to  disruptions in video quality than they are  to disruptions in the audio quality.

## 交互式会议应用-流媒体传输（发送）过程/原理：Interactive Applications: Media Transmission Path

工作，传输原理，

![](/static/2021-04-14-14-03-20.png)

那么，这些交互式会议应用程序之一是如何构建的呢？流媒体传播路径是什么样的？So how has one of these interactive  conferencing application structured?  What does the media transmission path look  like?

* **从某种捕捉装置开始**。start with some sort of  capture device.
  * <font color="red">可能是麦克风，也可能是摄像头，这取决于它是音频还是视频应用程序</font>。Maybe that's a microphone,  or maybe it's a camera, depending whether  it's an audio or a video application
* 【定期（通过捕获设备）采集媒体数据的帧数 Frames of media data are captured periodically】 --- <font color="deeppink">媒体数据从该设备捕获，并进入某种【输入缓冲区】，一次一帧</font> The media data is captured from that  device, and goes into some sort of  input buffer, frame at a time.
  * 如果是**视频**，那就是一帧一帧的视频。If it's video, it's each video frame  at a time.
  * 如果是**音频**的话，一次就是相当于20毫秒的语音或音乐数据帧 If it's audio,  it's frames of, typically, 20 milliseconds worth  of speech or music data at a  time.
* **【编解码器压缩媒体帧** Codec compresses media frames】 --- <font color="red">每个帧都从输入缓冲区获取，并传递给编码译码器。编码译码器逐帧压缩媒体帧</font>  Each frame is taken from that input  buffer, and passed to the codec.  The codec compresses the frames of media,  one by one.
* 【**压缩后的帧被分割成数据包** Compressed frames fragmented into packets】--- <font color="green">如果它们（多个帧）太大而不能放入一个单独的数据包，它就会将它们分解成多个数据包</font> if they’re too  large to fit into an individual packet,  it fragments them into multiple packets.
* 【**在UDP数据包内使用RTP协议传输** Transmitted using RTP inside UDP packets】 --- <font color="red">媒体帧的每个片段都通过将其放入 RTP 包(一个实时传输协议包)中进行传输，这个包被放入 UDP 包中，然后发送到网络上</font> Each of those fragments of a media  frame is transmitted by putting it inside  an RTP packets, a Real-time Transport Protocol  packet, which is put inside a UDP  packet, and sent on to the network. 
  * 【**RTP协议增加了定时和排序、源识别、有效载荷识别功能** TP protocol adds timing and sequencing, source identification, payload identification】
  * **RTP 包头添加了一个序列号，因此可以将包按正确的顺序放回**。增加了定时信息，使接收机能够精确地重构定时信息。 The RTP packet header adds a sequence  number, so the packets can be put  back into the right order.  It adds timing information, so the receiver  can reconstruct the timing accurately.
  * 它还添加了一些**源标识**，这样它就知道是**谁在发送媒体**，以及一些**有效载荷标识信息**，这样它就知道**哪种压缩算法，哪种编码译码器，被用来对媒体进行编码**。 And it  adds some source identification, so it knows  who's sending the media, and some payload  identification information, so it knows which compression  algorithm, which codec, was used to encode  the media.

:candy: 总结 ---**流媒体经过采集（捕获设备）、压缩（编解码器压缩帧）、碎片化（分包）、分组（UDP-RTP）、网络传输**。So the media is captured, compressed,  fragmented, packetised, and transmitted over the network.

## 接收媒体数据包过程（路径）/原理：Interactive Applications: Media Reception Path

接收端 - 接收媒体数据包过程/路径

![](/static/2021-04-14-15-22-26.png)

* 【**含有RTP协议数据的UDP数据包到达** UDP packets containing RTP protocol data arrive】**在接收端，包含 RTP 数据的 UDP 数据包到达**。 On the receiving side, the UDP packets  containing the RTP data arrive.
  * 接收应用程序从 UDP 数据包中提取 RTP 数据，<font color="red">并查看其中的源标识信息。然后根据发送者将数据包分离出来</font>  And the receiving application extracts the RTP  data from the UDP packets, and looks  at the source identification information in there.  And then it separates the packets out  according to who sent them.
* 【**信道编码器使用前向纠错修复损失** Channel coder repairs loss using forward error correction】<font color="red">对于每个发送方，数据通过一个信道编码器，，使用一个前向纠错方案，用于修复任何丢包</font> For each sender,  the data goes through a channel coder,  which repairs any loss, using a forward  error correction scheme  If one was used.
  * <font color="green">与流媒体一起发送的额外数据包，允许在不需要重传的情况下进行一些修复</font> Additional packets sent along with the media, to allow some repair without needed retransmission
* 【**回放缓冲区用于重建秩序，平滑时间** Playout buffer used to reconstruct order, smooth timing】**修复丢包损失后，媒体进入回放缓冲区**
  * 回放缓冲区足够缓冲，允许定时，以及定时的变化，以便重新构造顺序，**这样数据包就可以按照正确的顺序放回，这样它们就可以在正确的时间，以正确的定时行为，发送到编码译码器和解码器** The play-out buffer is enough buffering to  allow the timing, and the variation in timing, order to be reconstructed,  such that the packets are put back  into the right order, and such that  they're delivered to the codec, to the  decoder,  at the right time, and with the correct timing behavior
* 【**媒体被（解码器）解压缩，数据包丢失被隐藏（补偿），时钟偏斜被纠正** Media is decompressed, packet loss concealed, and clock skew corrected】
  * <font color="deeppink">然后解码器解压缩媒体，隐藏（补偿）任何剩余的丢包，纠正任何时钟偏差，纠正任何计时问题，</font> The decoder then decompresses the media,  conceals any remaining packet loss, corrects any  clock skew, corrects any timing problems,
* 【**将恢复后的媒体（解压器解压缩，损失补偿，时钟倾斜纠正，混合）呈现给用户** Recovered media is rendered to user】
  * 如果有不止一个人在讲话，则将其混合在一起，并将其呈现给用户。mixes it together if there's more than  one person talking, and renders it out  to the user.
  * 它把演讲或音乐播放出来，或者把视频放到屏幕上 It plays the speech  or the music out, or it puts  the video frames onto the screen.

## 传输多媒体的协议栈（标准）：Internet Multimedia Standards

用于通过互联网**传输多媒体的协议标准是什么样的** What does the set of protocol standards  which are used to transport multimedia over  the Internet, look like?

![](/static/2021-04-14-15-43-43.png)

* 相当复杂的协议栈。fairly complex protocol stack
* **其核心是互联网协议，IPv4和 IPv6，上面还有 UDP 和 TCP**。At its core, we have the Internet  protocols, IPv4 and IPv6, and UDP and  TCP layered above them.
* **UDP 流量之上的分层，是媒体传输流量和相关数据**。Layering above the UDP traffic, is the  media transport traffic and the associated data.
  * **UDP 数据包，用来传输数据** what you have there is the  UDP packets, which deliver the data
  * 还有一个数据报 **TLS 层**，协商加密参数 a datagram TLS layer, which negotiate the  encryption parameters;
  * **在TLS之上，有安全的 RTP 包，包含音频和视频数据，用于传输语音和图片** above that, sit the secure RTP  packets, with the audio and video data  in them, for transmitting the speech and  the pictures.
  * **还有一个称为 SCTP 的协议，它位于 DTLS 之上，用于提供对等数据通道** a protocol,  known as SCTP,  lead on top of DTLS, to provide  a peer-to-peer data channel.
  * **还有 NAT 穿越和路径发现机制**  NAT traversal and path  discovery mechanisms.
    * **使用诸如 STUN 和 TURN 以及 ICE 这样的协议来帮助建立点对点连接，以帮助发现 NAT 绑定** with protocols like STUN  and TURN and ICE to help set  up peer-to-peer connections, to help discover NAT  bindings.
* **有一个称为会话描述协议的协议 SDP**  【session  description protocol】, to describe the call being  set up
  * 这可以识别出那个试图建立多媒体呼叫的人，那个试图建立视频会议的人。它可以识别出他们想要交谈的人。this identifies the person who's trying  to establish the multimedia call, who's trying  to establish the video conference. It identifies the person they want to  talk to.
  * 它描述了他们想要使用的音频和视频压缩算法，他们想要使用的错误纠正机制，等等 It describes which audio and  video compression algorithms they want to use,  which error correction mechanisms they want to  use, and so on
* **【会话描述协议SDP】与【一组或多组信令协议一起使用】，具体取决于调用的设置方式** And session description protocol is used along with one or more of a set of signalling  protocols, depending how the call is being  set up
  * 它可以是**广播会话**的通知，例如使用称为**会话通知协议【SAP**】的协议 It may be an announcement of a  broadcast session, using a protocol called the  **Session Announcement Protocol**, for example
    * 【**宣布多播会议。SAP--过时**】Announcing multicast sessions: SAP – obsolete
  * 例如，可能是一个**电话呼叫**，使用【**会话发起协议SIP**】，这就是学校电话系统的工作原理
    * 【**交互式会议的控制。SIP**】Control of interactive conferencing: SIP
  * 它可能是一个**流式视频会话**，使用一个称为 【**RTSP 的协议**】 It might be a streaming video session,  using a protocol called RTSP.
    * 【**流媒体的控制**。RTSP】Control of streaming media: RTSP
  * 或者它可能是一个基于网络的视频会议应用程序，比如 Zoom，Webex ，或者微软Teams呼叫，**使用一个叫做 JSEP （Javascript Session establishprotocol）的协议通过 HTTP 进行协商**，。 OR it  might be a web based video conferencing  application, such as Zoom call, or a  Webex call, or a Microsoft Teams call, whether negotiation runs over HTTP using a  protocol called JSEP, the Javascript Session Establishment  Protocol.
    * 【**控制基于网络的互动媒体。JSEP（WebRTC**）】Control of web-based interactive media: JSEP (WebRTC)
  * **远程呈现的控制: CLUE-没有被广泛使用** Control of telepresence: CLUE – not widely used

## 媒体传输协议：Media Transport- RTP

媒体传输 media transport --- 一旦我们**采集（捕获设备）和压缩（编码译码器 codec）了数据**，并做好了传输的准备，我们**如何将音频和视频数据从发送方送到接收方**呢？How do we actually get the audio  and video data from the sender to  the receiver, once we've captured and compressed  data, and got it ready to transmit?

* 通过实时传输协议(RTP)的协议发送的 sent within a protocol called  the Real-time Transport Protocol, RTP

![](/static/2021-04-14-16-37-32.png)

:orange: RTP 协议由两部分组成，一个是**数据传输协议**，另一个是**控制协议** RTP comprises two parts. There's a  data transfer protocol, and there's a control  protocol.

* 独立的数据和控制信道 Separate data and control channels

:orange: <font color="red">RTP协议 --- 数据传输协议 （RTP数据协议）</font> The data transfer protocol is usually called  just RTP, the RTP data protocol

* 它**携带媒体数据**  it carries the media data.
* 它是以**一组【有效负载格式】的形式构成的** It’s structured in the form of a  set of payload formats.
  * 【特定的编码译码器数据包格式；应用级框架；强大但复杂的功能 Codec-specific packet formats; application level framing; robust, but complex】**有效负载格式描述了如何将每个特定的视频压缩/音讯压缩算法的输出映射到一组要传输的数据包上** The payload formats  describe how you take the output of  each particular video compression algorithm, each particular  audio compression algorithm, and map it onto  a set of packets to be transmitted
  * 【**每一帧都进行了分组，以便独立使用，实现低延迟** Each frame packetised for independent use for low latency】<font color="red">它描述了如何分割一帧视频，如何分割一系列音频数据包，这样每个 RTP 数据包，每个 UDP 数据包，即使丢失了一些数据包，也可以被独立解码</font> And it describes how  to split up a frame of video,  how to split up a sequence of  audio packets, such that each RTP packet,  each UDP packet, which arrives can be  independently decoded, even if some of the  packets have been lost
  * **它确保数据包之间没有依赖关系，这个概念称为应用程序级别帧**。 It makes sure  there's no dependencies between packets, a concept  known as application level framing.
* 【**数据报TLS握手--通常的TLS握手，但在UDP数据包内**。Datagram TLS handshake – usual TLS handshake but within UDP packets 】
  * **这将在数据报 TLS 层上运行，该层协商加密密钥和安全参数，使我们能够加密这些 RTP 包**  And this runs over a datagram TLS  layer, which negotiates the encryption keys and  the security parameters to allow us to  encrypt those RTP packets.

:orange: RTP控制协议 RTCP (real-time control protocol)

* 【信号源描述和呼叫者身份、接收质量、编解码控制 Source description and caller identity, reception quality, codec control】
  * 并提供诸如呼叫者身份、接收质量统计、重新传输请求等等服务，**以防数据丢失** provides things like Caller-ID, reception quality  statistics,  retransmission requests, and so one, in case  data gets lost.
* **提供各种扩展** Extensions
  * 【接收质量和用户体验监测 Reception quality and user experience monitoring】提供详细的用户体验和接收质量报告 detailed user experience and reception quality reporting,
  * **编码译码控制 & 其他反馈机制** Codec control and other feedback
    * 以检测和纠正数据包丢失 detect and correct packet loss,
  * **断路器和拥塞控制** Circuit breakers and congestion control
    * 如果质量太差，则执行断路器功能以停止传输 provide congestion control and perform circuit breaker  functions to stop the transmission if the  quality is too bad.

---

## RTP报头格式：Header

**【UDP中携带RTP数据包** RTP data packets carried within UDP】The RTP packets are sent inside UDP  packets.

:orange: **RTP 包格式(媒体数据的格式**) the  format of the RTP packets

* **位于 UDP 数据包的有效负载部分中** sits within the payload section of  UDP packets
* **头部信息携带** Header information carries:
  * **序列号和时间戳** Sequence number and timestamp
    * 以允许接收者重新构建排序，并重新构建时间 allow the receiver to reconstruct the ordering, and reconstruct  the timing. 
  * **源标识符** Source identifiers
    * <font color="red">来识别是谁发送的数据包 (如果为多方视频会议</font>) There’s a source identifier to  identify who sent the packet, if you  have a multi-party video conference
  * **有效载荷格式标识符** Payload format identifier
    * **描述它是否包含音频或视频,数据包是否包含音频或视频**？Does the packet contain audio or video? describe whether it contains audio or  video
    * **使用什么压缩算法** What compression algorithm is used?

:orange: 还有空间用于**扩展标头**，空间用于**padding**，还有空间用于放置**实际音频或视频数据的有效载荷数据**。And there’s space for extension headers,  and space of padding, and the space  for payload data where the actual audio  or video data goes.

## RTP收方-补偿抖动（包间时延）：Media Transport- Timing Recovery

![](/static/2021-04-14-16-59-51.png)

这些数据包，这些 RTP 数据包，是**在 UDP 数据包中发送的。发送者通常会【定时发送（以一定时间间隔）】这些信息** And these packets, these RTP packets,  are sent within UDP packets. And the  sender will typically send these with pretty  regular timing

* 如果是音频，它每秒会产生50个数据包; 如果是视频，它可能每秒产生25或30或60个帧率，但时间往往是可以预测的 If it’s audio, it generates  50 packets per second;  if it's video, it might be 25  or 30 or 60 frames per second,  but the timing tends to be quite  predictable.
* <font color="red">然而，当数据穿越(不同)网络时，时间间隔常常被其他类型的流量所扰乱，即网络中的交叉流量</font> As the data traverses the network,  though, the timing is often disrupted by  the other types of traffic, the cross-traffic  within the network
  * 如图，底部，接收端数据包时间不再可预测 If we look at  the bottom of the slide, we see  the packets arriving at the receiver,  and we see that the timing is  no longer predictable.
  * <font color="red">因为网络中的其他流量，因为它是一个尽最大努力的网络，因为它是一个共享网络，【媒体数据与 TCP 流量共享网络，与网络上的所有其他流量共享网络，所以数据包不一定会以可预测的时间到达】。</font> Because of the other traffic in the  network, because it's a best effort network,  because it's a shared network,  the media data is sharing the network  with TCP traffic, with all the other  flows on the network, and so the  packets don't necessarily arrived with predictable timing.
* 即，网络中的不同流队列延迟扰乱了时间间隔【网络会引起媒体流的时间抖动 Network induces timing jitter into the media stream】**接收端缓冲以消除时间间隔变化（抖动）** Variable queueing delays in the network disrupt timing – receiver buffers to smooth out variation

---

:candy: **收方必须做的事情之一，就是尝试重构时间（间隔**） One of the things the receiver has  to do, is try to reconstruct the  timing.

![](/static/2021-04-14-19-04-55.png)

* **数据传输的时间间隔** at the top, we see the timing  of the data as it was transmitted
  * 这个例子是显示音频数据，它标记了“谈话冲刺”，“谈话冲刺”是一个句子，或者一个句子的片段，中间有一个停顿。And the example is showing audio data,  and it’s labelling talk-spurts, and a talk-spurt  will be a sentence, or a fragment  of a sentence, with a pause between  it.
  * **我们看到包含语音数据的数据包以规则的间距传输。然后它们穿过网络，在某个时刻到达接收端** We see that the packets comprising the  speech data are transmitted with regular spacing.  And they pass across the network,  and at some point later they arrive  at the receiver.
  * 显然有一些延迟，在幻灯片上被标记为**网络传输延迟**，也就是**数据包穿越网络所需的时间** There's obviously some delay, it’s labeled as  network transit delay on the slide,  which is the time it takes the  packets to traverse the network.
  * **根据传播延迟信号，将会有个信号从发送端到接收端沿着网络传递所需的时间（最短时间），** there will be a minimum amount  of time it takes, just based on  the propagation delay, how long it takes  the signals to work their way down  the network from the sender to the  receiver.
  * **有不同数量的队列延迟，这取决于网络的繁忙程度** there will be varying amounts of queuing  delay, depending on how busy the network  is.
* <font color="red">结果就是，时间不再是固定的。数据包以固定的间距发送，到达时，它们之间偶尔会出现间隔</font> And the result of that, is that  the timing is no longer regular.  Packets which were sent with regular spacing,  arrive bunched together with occasional gaps between  them.
  * <font color="deeppink">它们可能无序到达，或者偶尔数据包可能完全丢失</font> And, occasionally, they may arrive out-of-order,  or occasionally the packets may get lost  entirely

:orange: **接收端需要利用“回放缓冲延迟”，来补偿/重构时间间隔变化 （补偿抖动**） And what the receiver does, is to  add what’s labeled as “playout buffering delay”  on this slide, to compensate for this  timing variation.

* 补偿所谓的**抖动**，即数据包在网络中传输所需时间的变化  To compensate for what's known  as jitter, the variation in the time  it takes the packets to transit across  the network
  * 时延抖动 -> 时延变化，【每个数据包之间的这种延时不一致称为抖动】
* <font color="red">通过增加一点缓冲时延，接收方可以允许自己的时间内将所有的数据包放回正确的顺序，并调整间隔</font> By adding a bit of buffering delay,  the receiver can allow itself time to  put all the packets back into the  right order,  and to regularise the spacing.
  * <font color="deeppink">它只是增加了足够的时延，使其能够补偿这种变化（抖动）。因此，通过在接收端上增加一点额外的时延，可以校正时间上的变化（抖动）</font>。It just  adds enough delay to allow it to  compensate for this variation. So, by adding  a little extra delay at the receiver,  the receiver correct for the variations in  timing.

---

### 为什么：回放前，添加一点时延？

![](/static/2021-04-14-19-22-10.png)

【如果数据包在到达时立即回放，时间上的变化（抖动）会导致数据包间间隙（回放失败）】**如果数据包一到达就被“回放”，这种变化（抖动）和时间间隔会导致间隙，因为数据包到达时间间隔不一致**。And, essentially, you can see, that if  the packets are played-out immediately they arrive,  this variation and timing would lead to  gaps, because the packets are not arriving  with consistent spacing.

* 【Delay playout by more than typical variation in inter-arrival time, to allow smooth play back】<font color="red">如果回放前添加 多于典型包间变化 的时延，就用足够缓冲用于回放（一旦开始回放数据），可以实现平滑播放/回放</font>  If you delay the play-out by more  than the typical variation between the inter-arrival  time of the packets,  you can add enough buffering that once  you actually start playing out the packets,  when you start playing out the data,  you can allow smooth playback.

:orange: **通过一点点额外的时延来换取非常平滑，一致的回放**  You trade  off a little bit of extra latency  for very smooth,  consistent, playback.

:candy: <font color="red">数据包到达之间的时延，媒体开始回放的时延，缓冲时延,一定程度上允许你重建时间，一定程度上给你时间来解压音频，解压视频，运行一个丢失隐藏算法，并且有可能重新传输任何丢失的数据包，这取决于网络往返时间</font>  And that delay between the packets arriving,  and the media starting to play back,  that buffering delay,  partly allows you to reconstruct the timing,  and it partly gives time to decompress  the audio, decompress the video, run a  loss concealment algorithm, and potentially retransmit any  lost packets, depending on the network round-trip  time.

* (回放后)，然后可以安排要播放的数据包，并且可以顺利地播放数据。 And then you can schedule the packets  to be played out, and you can  play the data out smoothly.

## RTP应用层数据包分组/装帧：Media Transport: Application Level Framing

关键的是，丢包是非常有可能的。**接收方必须充分利用到达的数据包** Packet loss is possible, so receivers must make best use of packets that do arrive

![](/static/2021-04-14-19-40-56.png)

:orange: **RTP有效载荷格式定义了如何将压缩的音频/视频数据格式化为RTP数据包** RTP payload formats define how compressed audio/visual data is formatted into RTP packets

* 在构建视频会议应用程序时，需要花费大量精力，来定义如何将压缩的视听数据格式化为RTP数据包 a lot of effort, when building  video conferencing applications, goes into defining how  the compressed audio-visual data is formatted into  the packets.
* **目标**：<font color="red">每个数据包都是独立可用的</font> each packet should be independently usable
  * 很容易获得视频压缩/视频方案和视频编解码器(编码译码器)的输出，**只需任意地将数据分组** It's easy to take the output of  a video compression scheme and a video codec,  and just arbitrarily put the data into packets
    * 【Naïve packetisation can lead to inter-packet dependencies where a packet arrives but can’t be decoded because some previous packet, on which it depends, was lost】<font color="red">但是，如果这样做，不同的数据包最终会相互依赖。如果早期的数据包丢失了，您就不能解码特定的数据包，因为这取决于早期数据包中的一些数据</font> But, if you do that, the different  packets end up depending on each other.  You can't decode a particular packet if  an earlier one was lost, because it  depends on some of the data was  in the earlier packet.
* 【**如果一个数据包到达，它应该有可能对它所包含的所有数据进行解码--这并不总是可能的，但有更好** If a packet arrives, it should be possible to decode all the data it contains – not always possible, but desirable】
  * **因此，构建视频会议应用程序的许多技巧都涉及到所谓的有效负载格式。它涉及到如何格式化视频压缩的输出结构，以及如何格式化音讯压缩的输出结构，这样，对于每个到达的数据包，它不依赖于任何数据在以前的数据包，在可能的范围内，以便每个到达的数据包可以被完全解码** So a lot of the skill in  building a video conferencing application goes into  what's known as the payload format.  It goes into the structure of how  you format the output of the video  compression, and how you format the output of  the audio compression, so that for each  packet that arrives, it doesn't depend on  any data that was in a previous  packet, to the extent possible, so that  every packet that arrives can be decoded  completely.
  * 这显然是有限度的。大多数视频压缩/视图方案都是通过发送一个完整的图像，然后对其进行编码来工作的，这显然意味着你依赖于之前的**完整图像，也就是我们所知道的索引帧**。  And there are obviously limits to this.  Most video compression schemes work by sending  a full image, and then encoding differences  to that, and that obviously means that  you depend on that previous full image,  what's known as the index frame. 
    * 如果索引帧丢失，这些系统中的许多都会建立重传机制，但是除了预测帧的数据包，在这之后传输的，都应该是独立可解码的。 And a lot of these systems build  in retransmission schemes if the index frame  gets lost, but apart from that the  packets for the predicted frames, that are  transmitted after that, should all be independently  decodable.

## 向前纠错vs重传：FEC（Forward Error Correction） vs. Retransmission

### 为什么实时应用不使用重传

显然，数据包可能会丢失，而网络应用程序(非实时应用)处理丢失数据包的典型方式是请求重传。你【可以通过视频会议应用程序清楚地做到这一点，**可以重传**】。**问题是重新传输需要时间** Obviously the packets can get lost,  and the way networks applications typically deal  with lost packets is by asking for  a retransmission.  And you can clearly do this with  a video conferencing application.  The problem is that retransmission takes time.

* 【**可以重传，但往往耗时太长--在重传到达之前，数据包应该已经回放完毕** Retransmission possible, but often takes too long – packet should have been played out before retransmission arrives】重新传输请求从接收方返回到发送方以及发送方传输数据**需要一个往返时间** It takes a round-trip time for the  retransmission requests to get back from the  receiver to the sender, and for the  sender to transmit the data.
  * <font color="red">但是对于视频会议应用程序，对于交互式应用程序，（实时应用）你有相当严格的延迟限制</font> But for video conferencing applications, for interactive  applications, you've got quite a strictly delay  bound
    * .延迟的范围大约是100-150毫秒，嘴对耳的延迟。这包括了捕捉一帧音频所需的时间. 音频帧通常是20毫秒，所以你有一个20毫秒的音频帧被捕获  The delay bound is somewhere on the  order of 100-150 milliseconds, mouth to ear  delay.  And that comprises the time it takes  to capture a frame of audio. audio frames are typically 20 milliseconds,  so you've got a 20 millisecond frame  of audio being captured
    * 然后需要一些时间来**压缩**这个帧。 然后它必须通过网络**发送**，这样你就有时间去运输网络。然后是对帧进行**解压缩**的时间，以及**回放**该帧音频的时间 And then it takes some time to  compress that frame. And then it has  to be sent across the networks,  so you've got the time to transit  network.  And then the time to decompress the  frame, and the time to play that  frame of audio out.
      * 这通常是四个帧的持续时间，加上网络时间。所以你有20毫秒的帧数据被捕获 And that typically  ends up being four framing durations,  plus the network time.
  * 所以你有20毫秒的帧数据被捕获。当这个帧被捕获的时候，前一个帧被压缩和传输。并且，在接收端，有一个帧被解码，错误被补充，并重构时序。然后另一个帧被回放出来。所以你有4帧，80毫秒，加上网络时间。**没有多少时间重新传输** So you have 20 milliseconds of frame  data being captured. And while that's being  captured, the previous frame is being compressed,  and transmitted. And, on the receiver side,  you have one frame being  decoded, errors being concealed, and timing being  reconstructed. And then another frame being played  out. So you've got 4 frames,  80 milliseconds, plus the network time.  It doesn't leave much time to do  a retransmission.
* <font color="purple">因此，重传在视频会议应用程序(实时)中往往不是特别有用，除非它们位于相当短的持续时间的网络路径上，因为它们到达得太晚了，无法及时回放</font> So retransmissions tend not to be particularly  useful in video conferencing applications, unless they're  on quite short duration network paths,  because they arrive too late to be played-out.

### 为什么使用向前纠错

Forward error correction (FEC) often used instead in real-time application

【额外的FEC数据包（包含纠错码 contain error correcting codes）与原始数据一起发送 Additional FEC packets are sent along with the original data】<font color="deeppink">前向错误更正的基本思想是，除了原始数据，你还可以发送更多的纠错包</font>  And the basic idea of forward error  correction is that you send additional error  correcting packets, along with the original data.

* **如例子**：发送了四包原始语音数据，原始媒体数据。对于这4个数据包中的每一个，你**会发送第五个数据包，也就是前向错误更正数据包** we're sending four packets of original speech  data, original media data. And for each  of those four packets, you then send  a fifth packet, which is the forward  error correction packet.
  * 因此，一组四个数据包被转换成五个数据包进行传输 So the group of four packets gets  turned into five packets for transmission.
  * 在这个例子中，**第三个数据包丢失了**  And, in this example, the third of  those packets gets lost.
  * <font color="red">在接收端，你接收到的五个数据包中的四个，然后用纠错数据恢复丢失，而不用重新传输数据包</font> at the receiver, you take the  four of those five packets which did  arrive,  and you use the error correcting data  to recover that loss without retransmitting the  packet.
* <font color="deeppink">这些纠错码有很多不同的工作方式。</font>  And there are lots of different ways  in which these error correcting codes can  work. 
  * 在**最简单的情况下，前向错误更正数据包只是对前面的数据包运行异或操作的结果**  In the simplest case, the forward error  correction packet is just the result of  running the exclusive-or, the XOR operation,  on the previous packets
    * 因此，幻灯片上的前向错误更正可以是，例如，数据包1、2、3和4的 XOR异或。. So the forward  error correction packets on the slides could  be, for example, the XOR of packets  1, 2, 3, and 4.
* 【**如果一些原始数据包丢失，但FEC数据包到达，可以重建原始数据**。If some original packets are lost but the FEC packets arrive, original data can be reconstructed】
  * <font color="green">在这种情况下（使用异或），在接收端上，当它发现【数据包3丢失】时，如果它计算接收到的数据包的异或，所以如果你【把异或数据包1、2和4和 FEC 数据包放在一起，会出现原始数据包丢失的数据包】</font> In this case, on the receiver,  when it notices that packet 3 has  been lost, if it calculates the XOR  of the received packets, so if you  XOR packets 1, 2, and 4,  and the FEC packet together, what will  come out will be the original packet,  missing packet.
  * 这显然是一个简单的方法。有很多更复杂的前向错误更正/服务计划，它们用不同的复杂程度换取不同的开销。  And that's obviously a simple approach.  There are a lot of much more  sophisticated forward error correction schemes, which trade  off different amounts of complexity for different  overheads.
  * <font color="red">但是这个想法是你偶尔发送数据包，这个错误纠正数据包，并且允许你在不重传数据包的情况下从某些类型的丢失中恢复，所以你可以更快地恢复丢失。</font>  But the idea is that you send  occasional packets, which error correcting packets,  and that allows you to recover from  some types of loss without retransmitting the  packets, so you can recover losses more  quickly.

# 数据信道：Data Channel

## 网页即时通信：Media: WebRTC Data Channel

![](/static/2021-04-14-21-09-56.png)

【**除视听媒体外，WebRTC还提供了一个点对点的数据通道**。In addition to audio-visual media, WebRTC provides a peer-to-peer data channel】除了发送视听媒体，**大多数视频会议应用程序还提供某种点对点数据通道**。这是 WebRTC 标准的一部分，也是大多数其他系统的一部分。In addition to sending audio visual media,  most video conferencing applications also provide some  sort of peer-to-peer data channel.  This is part of the WebRTC standards,  and it's also part of most of  the other systems as well.

* **目的**：（**提供视频会议应用额外一些支持，功能【支持网页浏览器进行实时语音对话或视频对话的API】**）我们的目标是提供像点对点文件传输这样的应用作为视频会议工具的一部分，支持聊天会话和音频和视频，并支持反应表情符号，举手的能力，请求说话者说话更快或更慢，等等。The goal is to provide  for applications like peer-to-peer file transfer as  part of the video conferencing tool,  to support a chat session along with  the audio and video, and to support  features like reaction emoji, the ability to  raise your hand, request to the speaker  talks faster or slower, and so on.

---

### 基于SCTP的WebRTC数据信道

![](/static/2021-04-15-10-37-15.png)

【**在安全的UDP隧道中使用SCTP的WebRTC数据通道** WebRTC data channel using SCTP in a secure UDP tunnel】在 **WebRTC** 中实现这一点（提供视频会议应用额外一些支持，功能）的方法是**使用一个名为 SCTP 的协议，该协议在一个安全的 UDP 隧道中运行** The way this is implemented in WebRTC,  is using a protocol called SCTP running  inside a secure UDP tunnel.

* **SCTP 是流控制传输协议**，它是取代 TCP 的先前尝试。SCTP is the Stream Control Transport Protocol,  and it was a previous attempt at  replacing TCP.
  * **【SCTP 的原始版本直接在 IP 上运行】，被认为是 TCP 的直接替代品，直接在 IP 层上作为 TCP 或 UDP 的对等机运行**  The original version of SCTP ran directly  over IP, and was pitched as a  direct replacement for TCP, running as a  peer for TCP or UDP directly on  the IP layer. 
* 事实证明，**这个系统太难部署了，所以没有得到大量的使用**And it turned out this was too  difficult to deploy, so it didn't get  tremendous amounts of take-up.
  * 但是，在 WebRTC 标准开发的时候，它是可用的，并且是特定的，人们认为将它移动到 UDP 之上运行，在 Datagram TLS 之上运行，提供安全性，作为一种提供可靠的对等数据通道的部署方式，是相对简单的 But, at the  point when the WebRTC standards were being  developed, it was  available, and specified, and it was deemed  relatively straightforward to move it to run  on top of UDP, to run on  top of Datagram TLS, to provide security,  as a deployable way of providing a  reliable peer-to-peer data channel.
  * 也许可以使用 TCP 来做到这一点，但是当时人们认为 TCP 的 NAT 穿越不是很可靠，而且通过 UDP 传输的 NAT 穿越效果更好 And it would perhaps have been possible  to use TCP to do this,  but the belief at the time was  that NAT traversal for TCP wasn't very  reliable, and that something running over UDP  would work better for NAT traversal.

:orange: **WebRTC 数据通道在【 DTLS-UDP之上使用 SCTP】，提供了一个透明的数据通道** the WebRTC data channel using  SCTP over DTLS over UDP,  provides a transparent data channel.

* 它提供了传递框架消息的能力，它支持通过单个连接传递多个子流数据，它还支持拥塞控制、重传、可靠性等等 It provides  the ability to deliver framed messages,  it supports delivering multiple sub-streams of data  over a single connection, and it supports  congestion control, retransmissions, reliability and so on
  * Message-oriented abstraction
  * Multiple sub-streams
  * Congestion controlled
* 【**使用WebRTC直接构建P2P应用** Makes it straight-forward to build P2P applications with WebRTC】**使用 WebRTC 可以直接构建P2P应用程序。【并且通过运行 UDP，获得了所有使用 QUIC 获得的部署优势】** And it makes it straightforward to build  peer-to-peer applications using WebRTC.  And gains all the deployments advantages that  we gained with QUIC, by running over  UDP.

### 为什么WebRTC数据信道不基于QUIC

![](/static/2021-04-15-10-41-37.png)

:orange: **为什么 WebRTC 使用 SCTP 来构建它的数据通道，而不是使用 QUIC**？Why not use QUIC? You might ask why WebRTC uses SCTP  to build its data channel, rather than  using QUIC?

* 从根本上说，这是因为 WebRTC 比 QUIC 的发展早 because WebRTC predates the  development of QUIC.
* 现在 QUIC 标准已经完成，**WebRTC 的未来版本很可能会迁移，转而使用 QUIC**，并逐步淘汰基于 sctp 的数据通道【Future versions of WebRTC will likely migrate to using QUIC】 It seems likely, now that the QUIC  standard is finished, that future versions of  WebRTC will migrate, and switch to using  QUIC, and gradually phase out the SCTP-based  data channel
  * QUIC比 SCTP，DTLS，UDP 堆栈更灵活，更高度优化【QUIC is more flexible and more highly optimised】 QUIC learned, from this  experience, and is more flexible and more  highly optimised than the SCTP, DTLS,  UDP stack.

## 信令协议 & 会话描述协议（SDP）：Signalling and Session Descriptions

:orange: 除了媒体传输和WebRTC数据（信道）之外，**还需要某种形式的信令和某种会话描述，以指定如何设置视频会议呼叫** （才能交换数据） In addition to the media transport and  data, you need some form of signalling,  and some sort of session description,  to specify how to set up a  video conferencing call.

> Signaling 信令服务器，也就是交换房间和会议的媒体信息，以及会议期间的消息，媒体描述使用的是 SDP 协议，

![](/static/2021-04-15-11-31-04.png)

:orange: **视频会议通话**为<font color="red">点对点</font> Video conferencing calls run peer-to-peer.

* 【**媒体传输流点对点，实现低延迟** Media transport flows peer-to-peer for low latency】
  * 像 Zoom、 Skype 或者其中任何一个系统的目标，就是在可能的情况下建立**点对点**数据，这样它们就可以达到尽可能**低的延迟** The goal  of a system like Zoom, or Skype,  or any of these systems, is to  set up peer-to-peer data, where possible,  so that they can achieve the lowest  possible latency.
* **如何建立点对点**？
  * 【<font color="red">需要一个【信令协议】来寻找接入点并建立路径</font> A 【signalling protocol】 is needed to find the peer and establish the paths】他们需要某种信令协议来做到这一点 They need some sort of signalling protocol  to do that.
  * 【**控制协议需要描述预期的通信会话** The control protocol needs to describe the communication session expected】
    * 【**会话描述协议(SDP**)为这些数据提供了一个】标准格式 Session description protocol (SDP) provides a standard format for such data】<font color="deeppink">一种标准化的做法是使用一种称为会话描述协议的协议。A standardised way of doing that is  using a protocol called the Session Description  Protocol.</font>
    * 需要的**媒体传输连接** Media transport connections required
    * **媒体格式和压缩算法**  Media formats and compression algorithms
      * 指定要使用的媒体格式（音频？音频和视频？）。使用哪些压缩算法。specify the media formats  they want to use （audio or audio & video, which compression algorithm are to be used?）.
    * **要使用的IP地址和端口** IP addresses and ports to use
      * **传达将要建立的传输连接的细节**，交换可以连接到他们的候选 IP 地址集，**建立P2P连接** They need some sort  of protocol to convey the details of  what transport connections are to be set  up, to exchange the set of candidate  IP addresses on which they can be  reached, to set up the peer-to-peer connection
    * **发起人和目的** Originator and purpose of session
    * **选项和参数** Options and parameters
      * 指定会话的时间、安全参数以及所有其他参数 sepcify the timing of the session and security parameters & other parameters.

:candy: 图例为某SDP，会话描述协议，简单描述多媒体会议 The example on the right at the  slide is an example of an SDP,  a Session Description Protocol, description of a  simple multimedia conference.

* SDP 的格式令人不快。它本质上是一组键值对，其中键都是单个字母，而且值更复杂，每行一个键值对，键值和值用等号分隔 The format of SDP is unpleasant.  It’s essentially a set of key-value pairs,  where the keys are all single letters,  and the values are more complex,  one key-value pair per line, with the  key and the value separated by equals  signs.
* **例子**：
  * 它以一个版本号 v = 0开始。这里有一条发送者专线，是由 Jane Doe 发起的，她的 IP 地址是10.47.16.5 it starts with a version number,  v=0. There’s an originator line, and it  was originated by Jane Doe, who had  IP address 10.47.16.5.  
  * 发起目的&发起源：这是一个关于会话描述协议的研讨会，它有设置呼叫的 Jane Doe 的电子邮件地址 It's a seminar about session description protocol.  It's got the email address of Jane  Doe, who set up the call,
  * 它得到了他们的 IP 地址，会话被激活的时间，它只接收，它的广播，这样监听者只是接收数据，它发送使用音频和视频媒体，它指定的端口和一些细节的视频压缩方案，等等 it's got their IP address, the times that session is active,  it's receive only, it's broadcast so that  the listener just receives the data,  it's sending using audio and video media,  and it specifies the ports and some  details of the video compression scheme,  and so on.
  * 格式的细节并不是特别重要。很明显，它在发送会话内容，IP 地址，时间，音讯压缩的详细信息，视频压缩的详细信息，要使用的端口号，等等。这些信息是如何编码的并不重要  The details of the format aren't particularly  important. It’s clear that it's sending  what the session is about, the IP  addresses, the times, the details of the  audio compression, the details of the video  compression, the port numbers to use,  and so on. And how this is  encoded isn't really important.

### SDP进行呼会话协商：Session Descriptions: SDP Offer/Answer

为了建立一个**交互式**通话，你需要某种形式的**协商**（会话间媒体协商） 【Interactive sessions require negotiation】 In order to set up an interactive  call, you need some sort of a  negotiation.

![](/static/2021-04-15-12-27-58.png)

* 通信**offer**：列出编码译码器、选项和地址细节、呼叫者身份。An offer to communicate: lists codecs, options and addressing details, identity of caller
  * 需要某种形式的offer来进行通信，它表示这是一组视频压缩/音讯压缩方案，这是一组发送方支持的方案 You need some sort of offer to  communicate, which says this is the set  of video compression schemes, this is the  set of audio compression schemes, that the  sender supports.
  * 是给你打电话的人。打电话的 IP 地址。是试图协商安全参数的公钥。 This is who is trying to call  you. This is the IP address that  they're calling you from. These are the  public key for trying to negotiate the  security parameters. And so on.
  * 以上组成一个offer，通过一个信令通道发送，通过一些超出界限的信令服务器，发给应答者（接电话的人） And that comprises an offer.  And the offer gets sent via a  signalling channel, via some out of bounds  signalling server, to the  responder, to the person you're trying to  call.
* **answer为编码译码器和选项的子集**，双方都能接受的，提供地址细节，并确认沟通的意愿 The answer subsets codecs and options to those mutually acceptable, supplies addressing details, and confirms willingness to communicate
  * **响应者生成一个answer**，<font color="red">该anser查看指定offer的一组编码译码器，并选择它也支持的子集</font>。The responder generates an answer, which looks  at that set of codecs the offer  specified, and picks the subset it also  understands
  * 它提供了可以到达的 IP 地址，提供了公钥，确认了通信意愿，等等。**answer会返回到最初的发送者**（通话的发起者） It provides the IP addresses it can  be reached at, it provides its public  keys, confirms its willingness to communicate,  and so on. And the answer flows  back to the original sender, the initiator  of the call. 
* **这允许发起方和应答方、交换建立呼叫所需的详细信息**。And this allows the offering party and  the answering party, the initiator and responder,  to exchange the details they need to  establish the call.
  * **offer包含所有的候选 IP 地址，可以用 ICE 算法来探测 NAT 绑定**   The offer contains all the IP address  candidates that can be used with the  ICE algorithm to probe the NAT bindings.  
    * ICE算法（→第2讲）探测NAT绑定，建立路径 ICE algorithm (→ Lecture 2) probes NAT bindings, establishes path
  * 回**来的answer包含接收者的候选集，允许他们做 STUN 交换，STUN 包，来运行 ICE 算法，建立点对点连接**。还有压缩算法、视频编解码器、音频格式、安全参数等详细信息 The answer coming back contains the candidates  for the receiver, that allows them to  do the STUN exchange, the STUN packets,  to run the ICE algorithms that actually  sets up the peer-to-peer connection.  And it's also got the details of  the compression algorithms, the video codec,  the audio formats, the security parameters,  and so on

#### SDP 会话间媒体协商 缺陷

:orange: 不幸的是，我们**最终使用的 SDP 作为协商格式，实际上并不是为此(协商)而设计的** Unfortunately SDP, which we have ended up  using as the negotiation format, really wasn't  designed to do this. 【SDP used as the negotiation format】

![](/static/2021-04-15-12-28-09.png)

* **它最初被设计为一种单向宣布格式，用来【描述】视频点播会议，而不是作为一种协商参数的格式** It was originally  designed as a one way announcement format  to describe video on demand sessions,  rather than as a format for negotiating  parameters
* 【**句法结构不足，语义超载**。Insufficient structure in syntax, semantic overloading】因此，语法非常令人不快，语义也非常令人不快，So the syntax is pretty unpleasant,  and the semantics are pretty unpleasant, 
* 【复杂→但复杂程度最初并不明显；现在太过根深蒂固，替代方案无法应用 Complex → but complexity not initially visible; now too entrenched for alternatives to take off】**而且在实践中使用起来有些复杂** it's  somewhat complex to use in practice.
  * 当我们开始开发这些系统，这些工具的时候，这种复杂性并没有真正显现出来，**但不幸的是，事实证明 SDP 在这里并不是一个很好的格式，【但是它现在已经太过根深蒂固，以至于替代品无法推广】**  And this complexity wasn't really visible when  we started developing the these systems,  these tools, but unfortunately it turned out  that SDP wasn't a great format here,  but it's now too entrenched  for alternatives to take off.
* SDP的设计不是为了表达各种选择和替代方案 SDP was not designed to express options and alternatives

### 交互会议-SIP协议建立P2P连接：Control of Interactive Conferencing: SIP

具体如何使用取决于您正在使用的系统，有两个广泛使用的模型 (信令模型？)Exactly how this is used depends on  the system you're using, There’s two widely  used models.  One is a system known as the  Session Initiation Protocol.

---

![](/static/2021-04-15-15-14-26.png)

:orange: <font color="deeppink">使用信令协议建立呼叫</font> The signalling protocol sets up the call（P2P？）

* 【**会话初始协议（SIP），用于电话和视频会议**。】Session Initiation Protocol (SIP) for telephony and video conferencing
  * 另外，SIP 会话发起协议在电话通信领域应用非常广泛，并且广泛应用于独立的视频会议系统 And the Session Initiation Protocol, SIP,  is very widely used for telephony,  and it's widely used for stand-alone video  conferencing systems.
  * **如**：如果你使用移动电话打电话，这是电话如何定位你想打电话的人，并设置呼叫，是使用 SIP  If you make a phone call using  a mobile phone, this is how the  phone locates the person you wish to  call, and sets up the call,  is using SIP, for example.
* 【**使用公共IP地址的会议服务器处理域名的呼叫** Conferencing servers with public IP addresses handle calls for a domain】
* 【**通过服务器，从发起者向应答者发送SIP消息** SIP messages sent from initiator to responder via servers】**SIP 依赖于一组会议服务器，一个代表打电话的人，另一个代表被调用的人** And SIP relies on a set of  conferencing servers, one representing the person making  the call, and one representing person being  called.
  * 而且这两个设备，现在通常是移动电话或电话，可以直接连接到这些服务器，它们一直被维护  And the two devices, typically mobile phones  or telephones these days, have a direct  connection to those servers, which they maintain  at all times.
  * **在发送端，当你试图打电话时，消息发送到服务器，这就允许，在这一点上有一组 STUN 数据包被交换，和一组信令消息被交换，允许发起者找到它的公共 NAT 绑定/然后消息传到服务器，服务器为被呼叫的人定位服务器，并通过连接将消息传回他们（响应者的）服务器，最终到达响应者**  On the sending side, when you try  to make a call, the message goes  out to the server, and that allows,  at that point there's a set of  STUN packets exchanged, and a set of  signalling messages exchanged, that allow the initiator  to find its public NAT bindings/  And then the message goes out to  the server, and that locates the server  for the person being called, and passes  the message back over the connection to  their server, and it eventually reaches the  responder.
    * 这就给了响应者候选地址，以及所有的连接细节，和编解码器参数，等等，它需要这些来决定是否接受这个呼叫，并开始设置 NAT 绑定  And that gives the responder the candidates  addresses, and all the connection details,  and the codec parameters, and so on,  needed for it to decide whether it  wishes to accept the call, and to  start setting up the NAT bindings.
    * 确定响应者的位置 Determine location of responder
    * 发现NAT绑定 Discover NAT bindings
    * 协商呼叫的参数和选项 Negotiate parameters and options for the call
    * 使用SDP交换offer/answer，描述正在创建的会话 An offer/answer exchange using SDP to describe the session being created
* 【之后**提醒应答用户--电话响铃! - 并同意建立一个电话** Alert the responding user – make phone ring! – and agree to setup a call】
  * **然后响应者响应，消息通过多个服务器返回到发起者，这就完成了offer-answer包交换** And it responds, and the message goes  back through the multiple servers to the  initiator, and that completes the offer answer  exchange. 
    * 【**在响铃的同时，NAT绑定的发现和连接探测也会发生**。NAT binding discovery and connection probing takes place while alerting】这时，他们可以开始运行 ICE 算法，发现 NAT 绑定 At that point, they can start running  the ICE algorithm, discovering the NAT bindings.
    * 此时，他们已经就参数达成了一致，他们使用了哪些编解码器，使用了哪些公钥，等等。  And they've already agreed the parameters at  this point, which codecs they using,  what public keys that are using,  and so on.
      * **这使得他们可以使用 ICE 算法建立一个点对点连接，并使用 STUN 建立一个点对点连接，通过这个连接媒体数据可以流动**。 And that lets them  set up a peer-to-peer connection  using the ICE algorithm, and using STUN,  to set up a peer-to-peer connection over  which the media data can flow.
* 【**然后媒体通过直接的点对点连接路径流动** Media then flows over direct peer-to-peer path】
  * 信令设置是一个**间接的连接**。数据从启动程序流向它们的服务器、响应者的服务器、响应者，然后通过服务器路径返回。**这种间接的信令设置允许创建直接的点对点连接** And it's an indirect connection set up.  The data flows from initiator, to their  server, to the responder’s server, to the  responder, and then back via the server  path.  And that indirect signalling setup allows the  direct peer-to-peer connection to be created.

### WebRTC基于浏览器会议：Browser-based Conferencing: WebRTC

【**WebRTC是另外一种信令协议** WebRTC is an alternative signalling protocol】**在更为现代的系统中，使用基于 WebRTC 浏览器的方法** In more modern systems, systems using the  WebRTC browser-based approach,

![](/static/2021-04-15-16-10-26.png)

* 相比SIP中发收两方的服务器，**WebRTC只用一个单个服务器来处理会议服务** trapezoid that we have in the  SIP world, with the servers representing each  of the two parties, tends to get  collapsed into a single server representing the  conferencing service.
  * 在本例中，服务器是诸如 Zoom 服务器、 Webex 服务器或 Microsoft team 服务器之类的东西 And the server, in this case,  is something such as the Zoom servers,  or the Webex servers, or the Microsoft  Teams servers.
  * 而且，它基本上遵循同样的模式。<font color="red">只是现在有一个单一的会议服务器来发起这个呼叫，而不是一个跨服务提供商(为每一方提供服务器)</font> And, it's essentially following the same pattern.  It’s just that there's a now a  single conferencing server that initiates the call,  rather than being a cross-provider, with server  for each party.

:orange: **在网络浏览器中实施** Implemented in web browsers

* 这就是基于浏览器的网络会议系统的工作原理，比如 Zoom，Webex，以及 team 等等 And this is how web browser-based conferencing systems  such as  Zoom, and Webex, and Teams, and the  like, work.
* 【**暴露标准的JavaScript API** Exposes standard JavaScript API】
  * Javascript 应用和网络应用程序在顶部。与浏览器中与 WebRTC API 对话 get your Javascript application and web-based  application sitting on top. This talks to  the WebRTC API in the browsers,
* 【**通过 HTTP 将信令消息发送到控制呼叫的 web 服务器** Signalling messages delivered via HTTP to web server controlling the call】**WebRTC API，提供了对会话描述的访问，您可以通过 HTTP GET 和 POST 请求与服务器交换会话描述，以确定应该如何设置通信的详细信息**。provides access to the session  descriptions which you can exchange with the  server over HTTP GET and POST requests  to figure out the details of  how the communication should be set up.
  * <font color="red">一旦完成(会话描述的交换)，你就可以启动数据通道，和媒体传输，并建立点对点的连接</font> And, once you've done that, you can  fire off the data channel, and the  media transport, and establish the peer-to-peer connections.
  * <font color="deeppink">因此，最初的信令是通过 HTTP 与控制通话的 web 服务器进行交换的</font> So the initial signalling is exchanged via  HTTP to the web server, that controls  that call.
* *WebRTC主要在连接建立阶段用到SDP，连接双方通过信令服务交换会话信息，包括音视频编解码器(codec)、主机候选地址、网络传输协议等* 【**使用 SDP 交换offer/answer**。 Offer/answer exchanges using SDP】
  * SDP 中的offer-answer通过与服务器交换，并与响应者交换，然后，当所有各方同意通信时，**服务器返回包含浏览器设置呼叫所需的详细信息的会话描述** The offer-answer exchange in SDP  is exchanged with the server, and that  exchanges it with the responder, and then,  when all the parties agree to communicate,  the server sends back the session description  containing the details which the browsers need  to set up the call.

:orange:【增加P2P数据通道 Adds peer-to-peer data channel】然后（交换完会话描述SDP包后）他们**建立了一个点对点的连接** And they  then established a peer-to-peer connection.

:orange: **媒体传输使用 RTP**-与 SIP 相同 Media transport uses RTP – same as SIP

#### WebRTC目的

:candy: 【旨在将视像会议纳入网络浏览器和网络应用程序 Designed to integrate video conferencing into web browsers and web applications】 **WebRTC目标是将视频会议功能集成到浏览器中，并允许服务器控制呼叫设置。视频会议应用程序在实践中运行良好**   And the goal is to integrate the  video conferencing features into the browsers,  and allows the server to control the  call setup.  .  Video conferencing applications work reasonably well in  practice.

## 交互式应用发展：Future Directions for Interactive Applications

那么，交互式应用程序会怎么发展？**这些类型的应用程序有两种发展方式**
So what's happening with interactive applications? Where  are things going?  I think there’s two ways these types  of applications are evolving.

* 【新媒体类型-全息，触觉，增强和虚拟现实 New media types – holographic, tactile, augmented and virtual reality】**一个是支持更好的质量，支持新型媒体**。显然，随着时间的推移，音频和视频的质量，以及帧率和分辨率，都在逐渐提高，我预计这种情况还会持续一段时间。One is supporting better quality, and supporting  new types of media. Obviously, over time,  the audio and the video quality,  and the frame rate, and the resolution,  has gradually been increasing, and I expect  that will continue for a while.
  * 还有人在谈论运行各种类型的扩增实境、虚拟现实、全息3 d 会议和通过网络传输触觉的触觉会议 There's also people talking about running various  types of augmented reality, virtual reality,  holographic 3D conferencing, and  tactile conferencing where you transmit a sense  of touch over the network.
  * 【对质量和延迟的更严格的要求。所有这些都可以符合所述的基本框架 Every stricter requirements on quality and latency, All can fit within the basic framework described】**其中一些可能对延迟有更严格的要求，对质量也有更严格的要求，但是，据我所知，它们都符合我们所描述的基本框架**。 And some  of these have perhaps stricter requirements on  latency, and stricter requirements on quality but,  as far as I can tell,  they all fit within the basic framework  we've described.
    * 它们都可以使用 RTP 或数据通道或类似的东西通过 UDP 进行传输。它们都符合相同的基本框架，增加一点缓冲来重构时间，优雅地降低了媒体传输的质量。They can all be transmitted over UDP  using RTP, or the data channel, or something  very like it. And they all fit  within the same basic framework, of add  a little bit of buffering to reconstruct  the timing, graceful degradation for the media  transport.
* Media over QUIC?
  * 积极的研究和标准化-预期这方面会有迅速的发展 Active research and standardisation – expect rapid developments in this space
  * 目前，我们有一个用于音频和视频数据的 RTP 混合协议，以及基于 **sctp 的数据通道。**
  * 很明显，**数据通道很快就会过渡到使用 QUIC**。还有相当多的活跃的研究，标准化，和讨论，关于是否有必要将音频和视频数据移动到 QUIC 上。人们正在为 QUIC 构建不可靠的数据报扩展来支持这一点，所以我认为我们很有可能最终通过点对点的 QUIC 连接同时运行音频、视频和数据通道，尽管关于如何工作的细节仍在讨论中。Currently, we have a mix of RTP  for the audio and video data,  and the SCTP-based data channel.  it's pretty clear, I think, that the  data channel is going to transition to  using QUIC relatively soon.  And there's a fair amount of active  research, and standardisation, and discussion, about whether  it makes sense to also move the  audio and video data to run over  QUIC.  And people are building unreliable datagram extensions  to QUIC to support this, so I  think it's reasonably likely that we’ll end  up running both the audio and the  video and the data channel over peer-to-peer QUIC connections, although the details of  how that will work are still being  discussed.

# 流媒体视频应用：Streaming Video

HTTP Adaptive Streaming （HAS）

## 流媒体视频应用-RTP/HTTPS：Streaming Video Applications

![](/static/2021-04-15-16-36-20.png)

那么，像 Netflix、 iPlayer 和 YouTube 这样的流媒体视频应用实际上是如何工作的呢？So how do streaming video applications,  such as Netflix, the iPlayer, and YouTube,  actually work?

* 【**RTP 可以用于流媒体视频** RTP should be usable for streaming video】你可能期望他们做的是，使用 RTP，和视频会议应用程序一样，以低延迟和容忍丢失的方式，在网络上传输视频。Well, what you might expect them to  do, is use RTP, the same as  the video conferencing applications, to stream the  video over the network in a low-latency  and loss-tolerant way.
  * 事实上，这就是过去流媒体视频，流媒体音频，应用程序的工作方式。 And, indeed, this is how streaming video,  streaming audio, applications used to work. 
  * 【**九十年代后期网络电视开发的实时流媒体协议(RTSP**)。使用单向 RTP 协议传送视频 Real-time Streaming Protocol (RTSP) developed by Real Networks for Internet TV in late 1990s。Uses unidirectional RTP for video delivery】早在20世纪90年代末，这个领域最流行的应用程序是 RealAudio，后来是 RealPlayer，当时它加入了视频支持。这完全符合你的预期。它通过 RTP 传输音频和视频，并且有一个单独的控制协议，即时串流协定控制协议，来控制播放。 Back in the late 1990s, the most  popular application in this space was RealAudio,  and later RealPlayer when it incorporated video  support.  This did exactly as you would expect.  It streamed the audio and the video  over RTP, and had a separate control  protocol, the Real Time Streaming Protocol,  to control the playback.
    * 非常低的延迟，丢包容忍 Very low latency, loss tolerant
* 【**大多数视频点播、电视和电影都是通过 HTTPS 传送的**  Most video-on-demand, TV, and movies are delivered instead over HTTPS】然而，现在大多数应用程序实际上是通过 HTTPS 传输视频的。 These days, though, most applications actually deliver  the video over HTTPS instead.
  * 【**性能明显下降，延迟非常高，但启动延迟不那么重要** Performance is significantly worse, very high latency, but startup latency is less critical】结果，他们的表现明显更差。他们有明显更高的延迟，和明显更高的启动延迟。  And as a result, they have significantly  worse performance. They have significantly higher latency,  and significantly higher startup latency.
  * 【**与内容分销网络更好地整合** Integrates better with content distribution networks】不过，他们这样做的原因是，通过 HTTPS 传输视频，他们可以更好地与内容发布网络集成。 The reason they do this, though,  is that by streaming video over HTTPS,  they can integrate better with content distribution  networks.

## CDN内容分布式网络：HTTP Content Distribution Networks

那么什么是内容分布式网络呢？So what is a content distribution network?

![](/static/2021-04-15-20-13-57.png)

* 【有一个广泛的、发展良好的内容分发基础设施 There is an extensive, well-developed, content distribution infrastructure】 **【内容分布网络是一种服务】，它提供了一套全局的网络缓存和代理，你可以用它来发布你的应用程序，你可以用它来发布网络数据，网络内容，组成你的应用程序或者你的网站**  A content distribution network is a service  that provides a global set of web  caches, and proxies, that you can use  to distribute your application, that you can  use to distribute the web data,  the web content, that comprises your application  or your website.
* 它们由 Akamai、 CloudFlare 和 Fastly 等公司运营。**这些公司运行着大量的全球网络代理，网络缓存** They're run by companies such as Akamai,  and CloudFlare, and Fastly. And these companies  run massive global sets of web proxies,  web caches
  * 他们从网站上接管特定内容的传递。 And they take over the  delivery of particular sets of content from  websites.
  * 作为一个网站运营商，你给的文件，图像，视频，你希望在 CDN 上托管，给 CDN 运营商。他们确保这些文件在整个网络中被缓存起来，放在靠近你的客户所在地的位置。**每个文件，或者图片，或者视频，都有一个唯一的 URL。CDN 管理这个 URL 的 DNS 解析，所以当你查找这个名字时，它会返回一个 IP 地址，这个 IP 地址对应于一个代理，或者一个缓存，这个缓存在物理上离你很近**  As a website operator, you give  the files, the images, the videos,  that you wish to be hosted on  the CDN, to the CDN operator.  And they ensure that they’re cached throughout  the network, at locations close to where  your customers are.  And each of those files, or images,  or videos, is given a unique URL.  And the CDN manages the DNS resolution  for that URL, so that when you  look up the name, it returns you  an IP address that corresponds to a  proxy, or a cache, which is located  near physically near to you
  * 而且服务器上有数据，这样响应速度就会很快，这样全世界的这些服务器的负载就会得到平衡 And that server has the data on  it such that the response comes quickly,  and such that the load is balanced  around these servers, around the world.
* 【提供和缓存 HTTP 内容非常有效 Highly effective for delivering and caching HTTP content】**这些 cdn，内容分布式网络，在传递和缓存 HTTP 内容方面非常有效** And these CDNs, these content distribution networks,  are extremely effective at delivering and caching  HTTP content.
  * 它们支持一些非常高容量的应用程序: 游戏发布服务，如 Steam，应用程序，如苹果软件更新，或 Windows 软件更新，以及大量流行网站。They support some extremely high volume applications:  game delivery services such as Steam,  applications like the Apple software update,  or the Windows software update, and massively  popular websites.  
* 【全球部署 -- 与大多数大型服务供应商达成协议，以托管缓存代理服务器 Global deployment – agreements with most large ISPs to host caching proxy servers】**他们有全球部署，他们与绝大多数的 isp 达成协议，在网络的边缘托管这些缓存，这些代理服务器**。And they have global deployments, and they  have agreement with the overwhelming majority of  ISPs to host these caches, these proxy  servers, at the edge of the network.
  * 因此，无论您在网络中的哪个位置，都离内容发布节点非常近。So, no matter where you are in  the network, you're very near to a  content distribution node.
* 【只能使用基于 http 的内容-不支持基于 rtp 的流 Only works with HTTP-based content – does not support RTP-based streaming】**不过，cdn 的一个局限性是它们只能处理基于 http 的内容**。 A limitation of CDNs, though, is that  they only work with HTTP-based content.
  * <font color="deeppink">它们是用来传递网络内容的。整个基础设施都是基于通过 HTTP 传递 web 内容，或者更典型的是这些 HTTPS。他们不支持基于 RTP 的流媒体。</font>  They’re for delivering web content. And the  entire infrastructure is based around delivering web  content over HTTP, or more typically these  HTTPS. They don't support RTP based streaming.

## HAS流媒体技术原理：HTTP Adaptive Streaming (a.k.a. MPEG DASH)

> MPEG-DASH，作为一种基于HTTP的自适应码率的流媒体传输解决方案，在2012年由ISO/IEC发表，正式成为国际标准。
> **MPEG-DASH使用现有的HTTP Web服务器基础设施**，不需要进行修改。不管是互联网连接的电视，电视机顶盒，台式电脑，智能手机，还是平板电脑，智能手机，**这些设备都可以采用DASH对互联网提供的多媒体内容（视频，电视，广播等）进行播放。**

如今，**流媒体视频**的传输方式是利用**内容分布网络** The way streaming video is delivered,  these days, is to make use of  content distribution networks.

![](/static/2021-04-15-20-38-02.png)

* 【**通过 HTTPS 传送视频，以利用内容分布网络** Deliver video over HTTPS to make use of content distribution networks】它使用 HTTPS 从 CDN 节点传递  It's delivered using HTTPS  from a CDN node.
  * 【**视频内容编码为多个块** Video content encoded in multiple chunks】在 Netflix 这样的系统中，视频的内容被编码成多个块 The contents of a video, in a  system such as Netflix, is encoded in  multiple chunks,，
    * **每个块通常包含大约10秒钟的视频数据**  where each chunk comprises,  typically, around 10 seconds worth of the  video data.【Each chunk encodes ~10 seconds of video】
    * **每个块都被设计为独立可解码的**  Each of the chunks is designed to  be independently decodable【每个块都是独立可解的】
    * 【每个块以不同的编码速率以不同的版本提供 Each chunk made available in many different versions at different encoding rates】**每个都有不同的版本，不同的质量率，不同的带宽** each is made  available in many different versions, at many  different quality rates, many different bandwidths.
* **清单文件提供了可用块的索引** A manifest file provides an index for what chunks are available
  * 这是一个索引，上面写着，在电影的前10秒，有6个不同的版本可供选择，这是每个版本的大小，以及每个版本的质量等级，这是一个可以从中检索的 URL。接下来的10秒，接下来的10秒，依此类推 It's an index,  which says, for the first 10 seconds  of the movie  there are these six different versions available,  and this is the size of each  one, and the quality level for each  one, and this is a URL where  it can be retrieved from.  And the same for the next 10  seconds, and the next 10 seconds,  and so on.
* **客户端获取清单，依次下载大量数据** Clients fetch manifest, download chunks in turn
  * 视频流的工作方式，111是客户端获取清单，查看块的集合，然后依次开始下载块 And the way the video streaming works,  111 is that the client fetches the manifest,  looks at the set of chunks,  and starts downloading the chunks in turn.
  * 【Standard HTTPS downloads】**并且它使用标准的 HTTPS 下载来下载每个块。** And it uses standard HTTPS downloads to  download each of the chunks
  * 【**但要监视下载速率，并选择获取下一个自适应比特率的编码速率** But monitor download rate, and choose what encoding rate to fetch next – adaptive bitrate】
    * 但是，当它这样做的时候（使用标准HTTPS下载每个块），<font color="deeppink">它会监控它成功下载的速度。还有。在此基础上，选择下一次获取的编码速率</font> But,  as it's doing so, it monitors how  quickly it’s successfully downloading. And. based on  that, it chooses what encoding rate to  fetch next.
* <font color="red">因此，它首先获取一个相对低速率的数据块，然后测量它下载的速度。</font>   So, it starts out by fetching a  relatively low rate chunk, and measures how  quickly it downloads.
  * 也许它只是获取一个编码为每秒500千比特的数据块，然后测量它实际下载的速度。它能够看到它是否真的能够以每秒500千比特的速度下载500千比特的视频。 Maybe it's fetching a  chunk that's encoded at 500 kilobits per  second,  and it measures how fast it actually  downloads. And it sees if it's actually  managing to download the 500 kilobits per  second video faster, or slower, than 500  kilobits.  If it's downloading slower than real-time,  it will pick a lower quality,  a smaller chunk, for the next time. 
  * 【**当他们下载的时候，依次播放大块的内容** Playout chunks in turn, as they download】<font color="red">而且，如果它的下载速度比实时更快，那么下一次它将尝试选择更高的质量、更高的速率、更大的块。</font> And, if it's downloading faster than real-time,  then it will try and pick a  higher quality, a higher rate, chunk the  next time.
    * 所以它可以调整下载视频的速度，为每个视频片段选择不同的质量设置。当它下载数据块时，它依次播放每一个数据块，同时下载下一个数据块 So it can adapt the  rate at which it downloads the video  by picking a different quality setting for  each of the chunks, each of the  pieces of the video.  And as it downloads the chunks,  it plays each one out, in turn,  while it's downloading the next chunk.

## 自适应流媒体协议-DASH: Chunked Media Delivery and Rate Adaptation

> DASH是基于HTTP的**动态自适应的比特率流技术**，使用的传输协议是TCP(有些老的客户端直播会采用UDP协议直播, 例如YY, 齐齐视频等). 和HLS, HDS技术类似， **都是把视频分割成一小段一小段， 通过HTTP协议进行传输，客户端得到之后进行播放**
> DASH 是 Dynamic Adaptive Streaming over HTTP的简称。是一种自适应比特率流媒体技术。可以通过HTTP Web服务器传送流媒体。

![](/static/2021-04-15-21-19-25.png)

* 【**每一块编码约10秒的视频内容** Each chunk encodes ~10 seconds of video content】
  * 每一段视频通常只有5到10秒的时间，或者差不多相当于视频内容的时间 And each of the chunks of video  is typically five or ten seconds,  or thereabouts, worth of video  content
* 【**每个块以不同的编码率(即不同的大小)被压缩多次**】Each chunk is compressed multiple times at different encoding rates (i.e., different sizes)
  * <font color="deeppink">每一个块都有多个不同的压缩时间，并且可以以多种不同的速率提供，例如，它可以有多种不同的尺寸</font> And each one is compressed multiple  different times, and it's available at multiple  different rates, and it’s available at multiple  different sizes, for example.
  * 【例如，Netflix 建议以10种不同的速率进行编码，如右图所示  e.g., Netflix suggests encoding at ten different rates, as show on the right】右边的图表给出了 Netflix 推荐的视频是如何编码的例子，从每秒235千比特，到320x240分辨率的超低分辨率视频，再到每秒5800千比特，即每秒5.8兆比特，一个完整的高清视频 And the chart on the graph,  on the right, gives an example of  how Netflix recommend videos are encoded,  starting at a rate of 235 kilobits  per second, for a 320x240 very low  resolution video, and moving up to 5800  kilobits per second, 5.8 megabits per second,  for a full HD quality video.
    * 你可以看到每10秒钟的内容有10个不同的质量等级，10个不同的大小。 You can see the each 10 second  piece of content is available at 10  different quality levels, 10 different sizes.

:orange: 【**接收方获取清单，包含每个块的所有版本的列表** Receiver fetches manifest, containing the list of all versions of all each chunk】<font color="deeppink">接收方从清单开始获取，这给了它所有不同块的索引，所有不同的大小，以及每个块在哪个 URL 上可用 And the receiver fetches the manifest to  start off with, which gives it the  index of all of the different chunks,  and all of the different sizes,  and which URL each one is available  at.</font>



### 速率调整

![](/static/2021-04-15-21-19-25.png)

:orange: 【**接收方依次获取每个块** Receiver fetches each chunk in turn】 **并且，当接收方获取数据块时，它尝试检索该数据块的 URL，这涉及到 DNS 请求，这涉及到 CDN 将其重定向到本地缓存**。And, as it fetches the chunk,  it tries to retrieve the URL for  that chunk, which involves a DNS request,  which involves the CDN redirecting it to  a local cache.

* 对于那个本地缓存，**当接收方下载那块视频时，它测量下载速率** 【<font color="red">测量下载率并与编码率进行比较</font> Measures download rate and compares to the encoding rate】  And for that local  cache, as it downloads that chunk of  video, it measures the download rate.
  * **如果下载速率低于编码速率，则为下一块切换到较低的速率** If download rate slower then encoding rate, then switch to lower encoding rate for next chunk
  * **如果下载速率快于编码速率，考虑为下一块提取更高的速率**  If download rate faster than encoding rate, consider fetching higher rate for next chunk
  * **根据 TCP 下载速率选择获取编码速率** Chooses encoding rate to fetch based on TCP download rate

## DASH速率调整 vs TCP拥塞控制：DASH and TCP Congestion Control

【DASH速率适应和 TCP/拥塞控制协议在不同的时间尺度下工作 DASH rate adaptation and TCP congestion control work at different time scales】我们有两个层次的速率适应 And we see what's happening is that  we've got two levels of rate adaptation going  on

![](/static/2021-04-15-21-41-22.png)

* **有动态自适应流，DASH 客户端，通过 HTTP 获取内容** On one level, we've got the dynamic  adaptive streaming, the DASH clients, fetching the  content over HTTP.
  * 他们一次获取10秒钟的视频，测量下载10秒钟视频所需的总时间 They're fetching ten seconds worth of video  at a time, measuring the total time  it takes to download that ten seconds  worth of video
  * 它们除以每个块中占用的字节数，得到了 chuck 的平均下载速率 And they’re dividing the  time taken by the number of bytes  in each chunk, and that gives them  an average download rate for chuck.
* **TCP连接的速率调整** over a  TCP connection.
  * 【**TCP 根据每个 RTT 调整一次拥塞窗口 TCP adjusts congestion window once per RTT**】TCP 在每个往返时间内调整拥塞窗口 TCP adapts  its congestion window every round-trip time.
  * 它遵循 **Reno 或 Cubic 算法，并遵循 AIMD** 方法 And it's following a Reno or a  Cubic algorithm, and it's following the AIMD approach.
    * 发送速率沿着锯齿状的模式，在慢启动和 TCP 拥塞避免阶段之后反弹 And, as you see at the  top of the slide, the sending rate’s  bouncing around following the sawtooth pattern,  and following the slow start and the  congestion avoidance phases of TCP.
* 【**DASH接收器测量TCP连接10秒内的平均下载速度**。DASH receiver measures average download speed of TCP connection over ~10 seconds】
  * **所以在很短的时间范围内，我们有很多变化，就像 TCP 所做的那样。然后平均下来，给出块的总体下载速率** So we've got quite a lot of  variation, on very short time scales,  as TCP does its thing. And then  that averages out, to give an overall  download rate for the chunk.
  * 【**根据平均 TCP 下载速度选择下一块的大小进行下载** Selects size of next chunk to download based on average TCP download speed】并且，根据 TCP 管理的整体下载速率，每块视频的平均下载时间超过10秒，选择下一块的大小进行下载。这个想法是，每个块都可以下载，至少是实时速度，理想情况下比实时速度快一点，这样下载就可以提前完成。 And, depending on the overall download rate  that TCP manages to get, averaged over  the ten seconds worth of video for  chunk,  that selects the size of the next  chunk to download. The idea is that  each chunk can be downloaded, at least  at real-time speed, and ideally a bit  faster than real-time, so the download gets  ahead of itself.

:orange: 【**使用 DASH 播放的视频在最初几秒的质量往往相对较差** Videos played using DASH often have relatively poor quality for first few seconds】

* 而且，当你开始在 Netflix 上看一部电影，或者在 iPlayer 上看一个节目，你经常会看到它开始的时候质量相对较差，在最初的几秒钟，然后质量在10或20秒左右的时候就上升了 And, when you start watching a movie  on Netflix, or watching a program on  the iPlayer, for example, you often see  it starts out relatively poor quality,  for the first few seconds, and then  the quality jumps up after 10 or  20 seconds or so.
* 【**接收方选择了保守的下载速率-相对较差的质量，小尺寸-开始，用它来测量连接下载速度，然后切换到更合适的速率** Receiver picked a conservative download rate – relatively poor quality, small size – to start，Uses that to measure connection download speed, then switches to more appropriate rate】
  * 这里发生的事情是，接收者选择一个保守的下载速率作为初始块，它选择一个相对低质量，相对较小的块，然后首先下载，然后测量它需要多长时间。And what's happening here, is that the  receiver’s picking a conservative download rate for  the initial chunk, it’s picking one of  the relatively low quality, relatively small,  chunks, and downloading that first, and measuring  how long it takes.
  * 而且，通常情况下，这是一个保守的选择，收方意识到可以下载，意识到块实际上下载速度更快，所以相当快地切换质量水平。而且，在前10秒、20秒、几个块消失之后，质量水平已经回升。And, typically,  that's a conservative choice, and it realises  it can actually download,  it realises that the chunks are actually  downloading faster, so it switches up the  quality level fairly quickly. And, after the  first 10, 20, seconds, after a couple  of chunks have gone, the quality level  has picked up.

## DASH时延：DASH Latency

![](/static/2021-04-15-22-25-49.png)

所有这一切的结果是，**流媒体视频需要相当长的时间才能开始**。当你开始在 Netflix 上播放一部电影，或者在 iPlayer 上播放一个节目时，通常需要几秒钟才能开始播放 A consequence of all of this,  is that it takes quite a long  time for streaming video to get started.  It’s quite common that when you start  playing a movie on Netflix, or a  program on the iPlayer, that it takes  a few seconds before it gets going.

* 【**由于以下原因，多秒播放延迟很常见:Multi-second playout latency common, due to:**】**如果视频是现场编码的话**，原因是，数据块的持续时间，播放缓冲，以及编码延迟， And the reason for this, is some  combination of the chunk duration, and the  playout buffering, and the encoding delays if  the video’s being encoded live.
  * 数据块的持续时间 Chunk duration
  * 播放缓冲 Playout buffering
  * 实时流媒体的编码延迟 Encoding delays for live streaming

:orange: 获取大块(通常为10秒钟) ，**您需要在任何时候都能完成一个大块。你需要在接收器中缓冲10秒的视频，这样你就可以在获取下一个视频（块）的同时播放这段视频（块）** Fetching chunks, which are typically 10 seconds  long, you need to have one chunk  being played out at any one time。You need to have 10 seconds worth  of video buffered up in the receiver,  so you can be playing that chunk  out while you're fetching the next one.

* 所以，你有一个主干正在播放出来，一个块被获取，所以你立即得到了两个块的缓冲 So you've got one trunk being played  out, and one being fetched, so you've  immediately got two chunks worth of buffering.
  * 这相当于20秒的缓冲时间 So that's 20 seconds worth of buffering.
* 再加上通过网络获取数据所需的时间，再加上如果数据是实时编码的，那么编码这个数据块所需的时间至少是 a，在编码之前它需要获取整个数据块，所以你至少还有另外一个数据块，所以还需要10秒  Plus the time it takes to fetch  over the network, plus, if it's being  encoded live, the time it takes to  encode the chunk which will be at  least a, it needs to  pull in the entire chunk before it  can encode it, so you've got at  least another chunk, so that'd be another  10 seconds.
  * 因此，由于10秒钟的块持续时间，你会得到大量的延迟  So you get a significant amount of  latency because of the ten second chunk  duration
* **你还需要足够的视频缓冲区**，这样如果 TCP 下载速率发生变化（可用容量发生变化）时，即一个视频块的下载速度比你预期的慢得多，你不希望此时视频播放完了 You also need enough chunks of  video buffered up, such that  if the TCP download rate changes,  and it turns out that the available  capacity changes, so a chunk downloads much  slower than you would expect, that you  don't want to run out of video  to play
  * 你需要足够的视频，但是要缓冲，如果某个视频需要很长时间，你有时间为下一个视频块降低到一个较低的速率，并且保持视频的播放，即使是在一个较低的级别，也不会停止 You want enough video but buffered up,  that if something takes a long time,  you have time to drop down to  a lower rate for the next chunk,  and keep the video coming, even at  a reduced level, without it stalling.
  * 如果视频不会被播完。得在你开始播放之前，下载一个完整的块。所以你下载并解压一个特定的块，当你这样做的时候，你正在播放之前的块，所有的东西叠加起来，延迟叠加起来 Without you running out of video to  play out.  So you’ve got to download a complete  chunk before you start playing out.  So you download and decompress a particular  chunk, and while you're doing that you're  playing the previous chunk, and everything stacks  up, the latency stacks up.
    * 【延迟加起来相当于一个块的持续时长，播完了相当于抵消了】
    * :sweat_smile: ，，看不懂，，，翻译你妈的臭嗨

---

Download and decompress chunk n
• Start playing chunk n and while playing download chunk n+1
• No packets lost → latency equals chunk duration

### 时延源1-TCP重传：Sources of Latency: TCP

事实上，只是缓冲了不同的视频块，你需要在下一个视频块下载时播放完整的视频块，**由于网络的原因，由于数据在网络上传输的方式，即延迟的来源** In addition to the fact that you're  just buffering up the different chunks of  video, and you need to have a  complete chunk being played while the next  one is downloading, you get the sources  of latency because of the network,  because of the way the data is  transmitted over the network.

![](/static/2021-04-15-22-50-19.png)

:orange: **TCP 重传丢失数据包的通常方式是遵循三重重复的 ACK**。 the usual way TCP retransmits lost  packets, is following a triple duplicate ACK.【If a packet is lost, TCP retransmits after triple duplicate ACK】

* 在发送端，**有一个用户空间，在那里数据块，视频块，被写入到 TCP 连接** on the sending  side, there is the user space, where the blocks of data, the chunks  of video, are being written into a  TCP connection.
  * 这些数据在内核中得到缓冲（在发送端的操作系统内核中），并通过网络传输。And these get buffered up in the  kernel, in the operating system kernel on  the sender side, and transmitted over the  network.
  * 过了一段时间，**它们到达接收端的操作系统内核，然后生成ACK**，因为这些数据块，就像 TCP 数据包（视频数据块），被接收 At some point later they arrive in  the operating system kernel on the receiver  side, and that generates the acknowledgments as  those chunks, as the TCP packets,  the chunks of video, are received.
* 【**这需要时间，而且会延迟大块的下载** This takes time, and delays download of the chunk:】<font color="deeppink">而且，如果一个数据包丢失了，它就开始产生重复确认。最终，在三重重复确认之后，数据包将被传输。我们发现这需要时间</font> And, if a packet gets lost,  it starts generating duplicate acknowledgments. And,  eventually, after the triple duplicate acknowledgement,  the packet will be transmitted.  And we see that this takes time.
  * 如果这是视频，**数据包以固定的速率发送**，我们看到发送四个数据包需要时间，丢失的数据包 + 后面的三个产生重复 ack 的数据包（在发送者发现数据包丢失之前）。【<font color="red">4 × 数据包传输延迟(丢失的数据包，加上后面三个产生重复 ack 的数据包)</font> 4× packet transmission latency (the lost packet, plus three following that generate duplicate ACKs)】 And if this is video, and the  packets are being sent at a constant  rate, we see that it takes time  to send four packets, the lost packet plus the three following that generate the  duplicate ACKS,  before the sender notices that a packet  loss has happened.
  * 【1 × 网络 RTT 重新传输数据包 1× network RTT to retransmit packet】另外，**需要一个往返时间**才能将确认发送回发送方，并重新传输数据包 Plus, it takes one  round trip time for the acknowledgements to  get back to the sender, and for  it to retransmit the packet.
  * 【**每一次数据包丢失和重传都会增加数据块下载时间，并导致 TCP 减少其窗口** Each packet loss and retransmission will increase chunk download time – and causes TCP to reduce its window】所以在此之前的时间，**如果一个数据包丢失了，它需要四个数据包传输时间，加上一个往返时间，然后数据包被重新传输，并返回到接收端**   So the time before, if a packet  has been lost, it takes four times  the packet transmission time, plus one round-trip  time, before the packet gets  retransmitted, and arrived back at the receiver
    * 这就增加了一些延迟。它至少需要增加四个包，加上一个往返时间，以及额外的延迟来应对单次重新传输 And that adds some latency. It’s got  to add at least four packets,  plus one round-trip time, extra latency to  cope with a single retransmission.
    * <font color="red">而且，如果网络不可靠，比如有多个数据包可能丢失，你需要增加更多的缓冲时间，增加额外的延迟时间，</font>And, if the network's unreliable, such that  more than one packet is likely to  be lost, you need to add in  more buffering time, add in additional latency,  to allow the packets to arrive,  such that they can be given to  the receiver without disrupting the timing.  So you need to add some latency  to compensate for the retransmissions that TCP  might be causing,
      * 以允许数据包到达，这样它们就可以在不扰乱时间的情况下发送给接收者 so that you can  keep receiving data smoothly while accounting for  the retransmission times.

:candy: 如果使用 TCP，两者都会增加延迟 Both add latency if using TCP

### 时延源2-块大小&视频压缩：Sources of Latency: Chunks and Video Compression

由于**视频块的大小**，也会造成一些时延。In addition, there’s some latency due to  the size of the chunks of video

![](/static/2021-04-16-11-40-28.png)

:orange: 【**每个视频块都是独立可解码的** Each chunk of video is independently decodable】<font color="red">每个块都必须是独立可解码的，因为您正在更改压缩，可能会更改每个块的压缩级别</font>Each chunk has to be independently decodable,  because you're changing the  compression, potentially changing the compression level,  at each chunk.

* 【**无法基于前一块进行压缩，因为前一块依赖于网络容量→不可预测性** Can’t compress based on previous chunk, because previous chunk depends on the network capacity → unpredictable】所以每一个都不能以前一个为基础。它们都必须从每个块的开始开始，因为你不知道之前出现了什么版本  So each one can't  be based on the previous one.  They all have to start from scratch at the beginning of each chunk,  because you don't know what version came before.

:orange: **视频压缩**是如何工作的，这一切都是**基于预测**  how video  compression works, it's all based on predicting

* 【**因此，每个块都以一个 i 帧开始，然后是 p 帧**。 Each chunk therefore starts with an I-frame, followed by P-frames】
  * <font color="red">你发送初始帧，即所谓的 I-frames，索引帧，**即一个完整的视频帧**。然后用于预测，在此基础上的，接下来的几帧（P帧，预真帧）</font>。You send initial frames, what are called  I-frames, index frames,  which give you a complete frame of  video. And then they predict, based on  that, the next few frames based on that.
  * 所以，在一个场景的开始，你会发送一个索引帧，然后，对于场景的其余部分（剩余帧），每一个连续的帧都会包含与前一帧的差值（即，与前一个索引帧的差值，P帧） So, at the start of a  scene, you’ll send an index frame, and then, for the rest of the  scene, each of the successive frames will just include the difference from the previous  frame, from the previous index frame.
* 【**I帧很大: 通常是20 × 大小的 p 帧** I-frames are large: often 20× size of P-frames】
  * <font color="red">发送索引帧的频率会影响编码率（编码效率↓，压缩开销↑），【因为索引帧很大】</font> And how often you send index frames  affects that the encoding rates, because the  index frames are big.
    * **如**：正在发送一个完整视频帧，预测帧小得多，I一般是P的20倍，They're sending a complete frame of video,  whereas the predicted frames, in between,  are much smaller. The index frames are  often, maybe, 20 times the size of  the predicted frames
* 【**如果块持续时间较短→与 i 帧数相比 p 帧数较少; 视频压缩/帧比率下降** If chunks are smaller duration → contain fewer P-frames compared to number of I-frames; video compression ratio goes down】
  * <font color="red">取决于你如何编码这些视频块，如果块更小（已知每个块必须以I帧起始，一个完整的帧），下一个数据块开始之前可以发送的P帧就越少</font> And depending how you encode the chunks,  if the smaller chunks are, because each  chunk of video has to start with  an index frame, it has to start  with a complete frame, the shorter each  chunk is, the fewer P-frames that can  be sent before the start of the  next chunk and the next index free.
* 【**在块大小和压缩开销之间进行权衡** Trade-off between chunk size and compression overhead】
  * 【**小块减少延迟，但需要更多压缩开销** Small chunks reduce latency, but need more data】<font color="deeppink">你可以把视频块变小，这样可以**减少系统的延迟**，但是这意味着你会有更频繁的索引帧(更大开销)</font> You can  make the chunks of video small, and that reduces the latency in the  system, but it means you have more frequent index frames.
    * 由于索引帧与预测帧相比较大，使得**编码效率下降，开销上升**，因此索引帧的频繁使用需要更多的数据 And the more frequent  index frames  need more data, because the index frames  are large compared to the predicted frames,  so the encoding efficiency goes down,  and the overheads go up.
  * 【**大块数据需要较少的数据，但会增加延迟，块持续时间<=2秒时间的压缩开销变得过高**  Large chunks require less data, but add latency，Overheads become excessive for chunk duration ≲2 seconds】
    * 往往会在发送频繁索引帧的开销变得过大之前，强制执行**大约两秒钟的下限**And this tends to enforce a lower  bound of around two seconds before the  overhead of sending the frequent index frames  gets to be too much.
    * 因此，**块的大小往往超过5,10秒，只是保持压缩开销低，以保持视频压缩（编码）效率，合理**。**这就是这些应用程序延迟的主要来源**  So chunk sizes tend to be more  than that tend to be 5,  10 seconds just keep the overheads down, to keep  the video  compression efficiency, reasonable.  And that's the main source of latency  in these applications.

## Future Directions for Streaming Video

:orange: 像 Netflix 这样的应用程序，像 iPlayer，显然是可以工作的。但是他们有**相对较高的延迟（DASH）**

* 因为你要一块一块地抓取视频，**而且每个视频块相当于5到10秒的视频，所以当你开始播放视频的时候，在它真正开始播放之前，你有5到10秒的等待时**间。So, this clearly works. Applications like Netflix,  like the iPlayer, clearly work.  But they have relatively high latency.  Because you're fetching video chunk-by-chunk, and each  chunk is five or ten seconds worth  of video,  you have five or ten seconds wait  when you start the video playing,  before it actually starts playing
  * 而且很难减少延迟，因为要权衡压缩效率，开销。And it's  difficult to reduce that latency, because of  the compression efficiency, because of the overheads.
* <font color="deeppink">但是，希望能尽可能减少时延</font>And it would be desirable, though,  to reduce that latency.
  * 这对于那些观看体育节目的人来说是需要的，因为流媒体应用程序的延迟比广播电视要高，所以，如果你正在观看体育直播，你倾向于看到比广播电视慢5秒、10秒、20秒的动作，这是有问题的。  It will be desirable for people who  watch sport, because the latency for the  streaming applications is higher than it is  for broadcast TV,  so, if you're watching live sports,  you tend to see the action 5,  or 10, or 20, seconds behind broadcast  TV, and that can be problematic.
  * 对于那些试图提供交互式应用程序的人来说，这也是一个问题，他们希望扩增实境的延迟足够低，以便你可以与内容交互，也许还可以动态地改变视角，或者与视频的某些部分交互。It’s also a problem for people trying  to offer interactive applications, and augmented reality,  where they'd like the latency to be  low enough that you can interact with  the content, and maybe dynamically change the  view point, or interact with parts of  the video.

:orange: 因此，人们正在寻求**建立低延迟的流媒体视频传输**。有两种可能性 So people are looking to build lower-latency  streaming video.  I think there's two ways in which  this is likely to happen.

* 【**Streaming video over WebRTC. 用于交互式会议，但完全可用于基于 rtp 的视频流传输到浏览器**  Intended for interactive conferencing, but entirely usable for RTP-based video streaming to browser】可能会回到使用 **RTP， WebRTC** 这样的东西来控制传输建立，使用类似交互式视频会议的平台来构建流媒体视频，但只能**单向发送**。这在今天是可能的 The first is that we might go  back to using RTP.  We might go back to using something  like WebRTC to control the setup,  and build streaming video using essentially the  same platform we use for interactive video  conferencing,  but sending in one direction only. And this is this is possible today. 
  * 【**现在所有的 Web 浏览器都支持 WebRTC**。Web browsers now all support WebRTC】浏览器支持 WebRTC，而且<font color="red">没有任何规定说必须在 WebRTC 会话中发送和接收信息</font> The browsers support  WebRTC, and there's nothing that says you  have to transmit as well as receiving  in a WebRTC session.
  * 【**WebRTC 比 DASH 的延迟时间短得多** Much lower latency than DASH】因此，您可以使用 WebRTC 构建一个应用程序，将视频流传输到浏览器。**与目前人们使用的基于 dash----基于 HTTP 的动态自适应流技术相比，它的延迟时间要短得多** So you could  build an application uses WebRTC to stream  video to the browser.  It would have much lower latency than  the DASH-based, dynamic adaptive streaming over HTTP  based, approach that people use today.
  * 【Will this encourage CDNs to support RTP?】 <font color="deeppink">但目前还不清楚它是否能在内容分布式网络中发挥作用。【目前还不清楚 cdn 是否支持 RTP 流。】但是如果 CDNs 可以被说服，并支持 RTP，这将是一个获得更低延迟的好方法</font>  But it's not clear that it would  play well with the content distribution networks.  It’s not clear that the CDNs would  support RTP streaming.  But if they did, if the CDNS  could be persuaded to support RTP,  this would be a good way of  getting lower latency.
* 【Streaming video over QUIC】更有可能的是，我们将看到 cdn 开始支持 QUIC，因为它总体上为网络流量提供了更好的性能，然后人们开始**切换到通过 QUIC 传输流媒体视频** I think what's perhaps more likely,  though, is that we will start to  see the CDNs switching to support QUIC,  because it gives better performance for web  traffic in general,  and then people start to switch to  delivering the streaming video over QUIC.
  * 而且，因为 **QUIC 是一个用户空间栈**，所以更容易部署传输协议创新。因为他们只需要部署一个新的应用程序，你**不需要改变操作系统内核，不需要改变**，如果你想改变 TCP 的工作方式，你必须改变操作系统。然而，**如果你想改变 QUIC 的工作方式，你只需要改变提供 QUIC 的应用程序或库**    And, because QUIC is a user space stack, it's easier to deploy interesting transport  protocol innovations. Because they're done by just  deploying a new application, you don't have  to change the operating system kernel,  you don't have to change,  if you want to change how TCP  works, you have to change the operating  system. Whereas if you want to change the way QUIC works, you just have  to change the application or the library that's providing QUIC
    * 在用户空间中实现一个传输层协议有助于协议的快速迭代，协议的演进更为容易，不需要客户端和服务器更新其操作系统内核就能部署新的QUIC版本
  * 【**HTTP/3是现有 HTTP 堆栈的直接替换，通过 QUIC 传递请求** HTTP/3 is a drop-in replacement for existing HTTP stack, and delivers requests over QUIC】
    * **很有可能看到 cdn 转向使用 HTTP/3，和通过 QUIC 传输 HTTP，也很有可能转向通过 QUIC 传输视频** it's likely that we  will see CDNs switching to use HTTP/3,  and HTTP over QUIC,  and I think it's likely that they'll  also switch to delivering video over QUIC
    * **这样可以更灵活地改变 QUIC 的工作方式，优化它以支持低延迟视频** And I think that gives much more  flexibility to change the way QUIC works,  to optimise it to support low-latency video.
    * 【YouTube 已经以这种方式通过 QUIC 提供视频——希望看到更多的部署和特殊用途的 QUIC 视频扩展YouTube already delivers video over QUIC this way – expect to see much more deployment and special purpose QUIC extensions for video】
      * YouTube已经在通过QUIC传输视频。 有人在IETF中讨论对QUIC的数据报扩展，以获得低延迟，所以我认为我们很可能会看到**视频切换到由CDNs使用QUIC交付，但使用一些QUIC扩展来提供更低的延迟**。YouTube is already delivering  video over QUIC.  There are people talking about datagram extensions  to QUIC in the IETF to get  low latency, so I think we’re likely  to see the video switching to be  delivered by the CDNs using QUIC,  but with some QUIC extension to provide  lower latency.

