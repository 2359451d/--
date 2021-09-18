# Data Breach

content:

* [Data Breach](#data-breach)
  * [Definition](#definition)
  * [Reason: What Caused the Data Breach](#reason-what-caused-the-data-breach)
    * [SQL Injection](#sql-injection)
      * [What can be done](#what-can-be-done)
    * [Insecure Data Storage](#insecure-data-storage)
      * [What can be done](#what-can-be-done-1)
    * [Insecure Communication](#insecure-communication)
      * [What can be done](#what-can-be-done-2)
    * [Unintended Data Leakage](#unintended-data-leakage)
      * [What can be done](#what-can-be-done-3)
    * [Insufficient Transport Layer Protection](#insufficient-transport-layer-protection)
      * [What can be done](#what-can-be-done-4)
  * [Reference](#reference)

## Definition

What is data breach?

> A data breach comes as a result of a cyberattack that **allows cybercriminals to gain unauthorized access to a computer system or network and steal the private, sensitive, or confidential personal and financial data of the customers or users contained within**.

a data breach goes **undiscovered for an average of 197 days**

* It takes another **69 days to remediate** the data breach
* By the time the data breach is fixed and discovered, malicious users could have the unfettered access to the database.

---

However, data breach isn't a threat or attack in its own right and **it is as a result of cyberattack** that allows cybercriminals to gain unauthorized access to a computer system or network and steal the data.

* with most data breaches, users' name, email addresses, passwords and credit card numbers would be stolen

## Reason: What Caused the Data Breach

### SQL Injection

> a type of attack that exploits weaknesses in the SQL database management software of unsecure websites in order to get the website to spit out information from the database that it’s really not supposed to. Here’s how it works.
>
> A cybercriminal enters malicious code into the search field of a retail site, for example, where customers normally enter searches for things like “top rated wireless headphones” or “best-selling sneakers.” Instead of returning with a list of headphones or sneakers, the website will give the hacker a list of customers and their credit card numbers. SQLI is one of the least sophisticated attacks to carry out, requiring minimal technical knowledge. Malwarebytes Labs ranked SQLI as number three in the [The Top 5 Dumbest Cyber Threats that Work Anyway](https://blog.malwarebytes.com/cybercrime/2017/04/the-top-5-dumbest-cyber-threats-that-work-anyway/). Attackers can even use automated programs to carry out the attack for them. All they have to do is input the URL of the target site then sit back and relax while the software does the rest.

#### What can be done

* **Update your database management software**
  * Cybercriminals can take advantage of these software vulnerabilities, or exploits, with a SQLI. You can protect yourself by just patching and updating your database management software
* **Enforce the principle of least privilege (PoLP)**
  * PoLP means each account only has enough access to do its job and nothing more.
  * For example, a web account that only needs read access to a given database shouldn't have the ability to write, edit or change data in any way.
* **Use prepared statements or stored procedures**
  * As opposed to dynamic SQL, prepared statements limit variables on incoming SQL commands.
  * In this way, cybercriminals can't piggyback malicious SQL injections onto legitimate SQL statements.
  * Stored procedures similarly limit what cybercriminals are able to do by storing SQL statements on the database, which are executed from the web application by the user

### Insecure Data Storage

> Most apps store some kind of information about their users, often referred to as personally identifiable information (PII). This can range from access tokens and option preferences to medical history and credit card details. If this data is not stored securely, hackers can access it and use it for a number of purposes.

How hackers exploit insecure data storage to steal data

* **Though the os**: includes how and where the OS stores data, images, tokens, and binary data
* **Development framework**: How and where an app stores data,images, log files and how it syncs to the cloud

---

#### What can be done

* it is essential to **encrypt all sensitive info and data** stored in the application and **enforcing proper authorizations**
* make sure **the key using to encrypt the data is also well protected**
* implement security measures beyond os, eg. deploy **[application shielding techonologies](https://www.intertrust.com/blog/application-shielding-for-secure-applications/) & protect encrpytion keys**

### Insecure Communication

> Mobile apps constantly send and receive data in a client-server arrangement, either through a telecom carrier or the internet. Even apps that can be used offline periodically connect to a server to receive updates. Hacker’s can eavesdrop on these  transmissions  to steal sensitive data coming from a user’s device or change the data that is being sent to it.
>
> common in Man-in-the-middle and phishing site attacks.

If hackers intercepts communication, sensitive data(including encryption keys, personal data, password .etc) can all become visible.

How hackers exploit app communication data

* The initial [SSL/TLS handshake](https://www.intertrust.com/blog/what-is-tls-and-how-to-ensure-a-secure-implementation/) is properly verified, but **there is no ongoing verification or the cipher suite negotiated is weak in itself**.
* **The certificate used by the server in the TLS handshake is not verified**, meaning the subsequent communication established is not based on valid security.
* **The network carrying the communications may have been compromised**. This includes Wi-Fi networks or local network routers.
* **There may be malware already on the device or a compromised app which spies on communications**.
* **Data being tampered** with while it is being transmitted between a server and client device, known as a *man-in-the-middle attack*.

---

#### What can be done

* Ensuring **authentication certificates are being signed by a trusted Certificate Authority (CA)** and not accepting self-signed certificates
* Employing industry-leading cipher suites that use sufficient key lengths(AES 128 & above)
* **using an end-to-end TLS security solution**
  * such as [whiteCryption’s SKB for TLS](https://www.intertrust.com/news/whitecryption-secure-key-box-skb-adds-tls-support-for-end-to-end-data-security-across-networks/) can ensure that session, and cryptographic keys cannot be compromised
* **Flaws in network traffic or invalid certificate usage can be flagged to users**
  * allow them to make a decision on ending or continuing communication

### Unintended Data Leakage

> Unintended data leakage occurs when a developer inadvertently places sensitive information or data in a location on the mobile device that is easily accessible by other apps on the device. First, a developer’s code processes sensitive information supplied by the user or the backend. During that processing, a side-effect (that is unknown to the developer) results in that information being placed into an insecure location on the mobile device that other apps on the device may have open access to. Typically, these side-effects originate from the underlying mobile device’s operating system (OS). This will be a very prevalent vulnerability for code produced by a developer that does not have intimate knowledge of how that information can be stored or processed by the underlying OS. It is easy to detect data leakage by inspecting all mobile device locations that are accessible to all apps for the app’s sensitive information.

Where hackers exploit this vulnerability

* mobile malware
* modified versions of legitimate apps
* an adversary that has physical access to the victim’s mobile device

---

#### What can be done

* **Avoid URL data Cashing**: developers should configure android to forbiden the network data cashing
  * configure the HTTP cache header in web server, `Cache-Control: no-sotre`
* **Avoid GUI Cashing**: An attacker who finds or steals a device can see directly the interface previously viewed by the user and see all the data still displayed on the Gui
  * **directly terminate entire app while user logout**: violates the android design principles but more secure
  * **check the user login status in each activity interface**: if user not login in then redirect
  * **clear the data in gui**: while user leaving or switching the pages
* **Constrain the username cashing**:if cashing username, it would be loaded in memory before any credentials, increasing the posibility of interception
  * **store the shielding username rather the real one**: Replace username with hash value in authentication (this could be the device token acquire in sign up process).
* **Be careful to keyboards cashing**: malware can extract user data by fetching the keyboard cache
  * **Disable autocorrect for any sensitive information**
* **Disable Copy/Paste Caching**: if the user is copying text-sensitive data, other apps can access it by accessing the clipboard
  * where appropriate, disable copy/paste processing of sensitive data
* **Screen capture and recording precautions**: malicious programs to record the user’s phone screen without the user’s knowledge
  * In privacy-related activity such as Login, payment, **adding functionality which prevents screen capture or recording**

### Insufficient Transport Layer Protection

> In most scenarios, there are data interactions between frontend, backend and third party services, so it is inevitable to transmit sensitive data in the network, there may be a risk of leakage of sensitive information.

To detect basic flaws, observe the phone’s network traffic. More subtle flaws require inspecting the design of the application and the applications configuration.

* If the hackers intercepts an admin account, the entire site could be exposed
* **Poor SSL setup** can also facilitate phishing and MITM attacks

---

#### What can be done

* **Use SSL protocol** (or SSL-based protocol) to transfer sensitive data
* **Alert users** through the UI if the mobile app **detects an invalid certificate**
* **Use strong, industry standard cipher suites with appropriate key lengths**
* **Use certificates signed by a trusted CA provider**
  * Never allow self-signed certificates, and consider certificate pinning for security conscious applications
* **Do not send sensitive data over alternate channels** (e.g, SMS, MMS, or notifications)

## Reference

[Data Breach - What is it?](https://www.malwarebytes.com/data-breach/)

[OWASP MOBILE TOP 10](https://owasp.org/www-project-mobile-top-10/)

[What is Application Shielding and How it Can Help Secure Your Applications](https://www.intertrust.com/blog/application-shielding-for-secure-applications/)