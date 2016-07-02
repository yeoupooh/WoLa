package com.subakstudio.wola

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import static groovyx.net.http.Method.POST

def addCookies(resp, cookies) {
    resp.getHeaders('Set-Cookie').each {
        String cookie = it.value.split(';')[0]
        println "Adding cookie: [$cookie]"
        cookies.add(cookie)
    }
}

def get(url, path, query, exheaders, cookies) {

    def html;
    println "http: get: url=[$url] path=[$path]"
    println "           query=[$query]"
    println "           exheaders=[$exheaders]"
    println "           cookies=[$cookies]"

    http.request(url, Method.GET, ContentType.TEXT) { req ->
        requestContentType = ContentType.XML
        uri.path = path
        uri.query = query
        headers.put('Cookie', cookies.join(';'))
        headers << exheaders

        println "http: get: req: headers=[$headers]"

        response.success = { resp, reader ->
            html = reader.getText()
            addCookies(resp, cookies)

            println "http: get: res: html=[$html]"
            println "http: get: res: cookies=[$cookies]"
        }

        response.failure = { resp, reader ->
            println "failure: statusLine=" + resp.statusLine
            println "failure: text=" + reader.text
        }
    }

    println "http: get: done."

}

def login(config, cookies) {

    def url = config.url
    def username = config.username
    def password = config.password
    def loginAuth = (username + ':' + password).bytes.encodeBase64().toString()

    get(url, '/index.asp', [:], [:] << baseHeader, cookies)

    println "login: url=[$url] auth=[$loginAuth]"

    http.request(url, POST, ContentType.TEXT) {
        uri.path = '/login.cgi'
        headers << baseHeader
        requestContentType = ContentType.URLENC
        body = [
                group_id           : '',
                action_mode        : '',
                action_script      : '',
                action_wait        : 5,
                current_page       : 'Main_Login.asp',
                next_page          : 'index.asp',
                login_authorization: loginAuth,
        ]

        response.success = { resp, reader ->
            html = reader.getText()
            addCookies(resp, cookies)

            println "login: post: res: html=[$html]"
            println "login: post: res: cookies=[$cookies]"
        }

        response.failure = { resp, reader ->
            throw new RuntimeException(reader.text);
        }
    } // http.request
}

def logout(config, cookies) {
    def url = config.url
    get(url, '/Logout.asp', [:], [Referer: "$url/Main_WOL_Content.asp <$url/Main_WOL_Content.asp>",] << baseHeader, cookies)
}

def doWakeUp(config, cookies) {
    def mac = config.mac
    def url = config.url
    println "wakeup: waking..."
    get(url, '/apply.cgi', [current_page   : 'Main_WOL_Content.asp',
                            next_page      : 'Main_WOL_Content.asp',
                            group_id       : '',
                            modified       : '0',
                            action_mode    : ' Refresh ',
                            action_script  : '',
                            action_wait    : '',
                            first_time     : '',
                            preferred_lang : 'EN',
                            SystemCmd      : "ether-wake -i br0 $mac",
                            firmver        : '3.0.0.4',
                            destIP         : mac,
                            wollist_macAddr: ''
    ], [Referer: "$url/Main_WOL_Content.asp <$url/Main_WOL_Content.asp>",] << baseHeader, cookies)
}

def wakeUp(options) {
    def config = options

    // Initial cookie
    def now = Calendar.instance.time.time
    def cookies = [
            'traffic_warning_0=2015.11:1',
            "nwmapRefreshTime=$now"]

    // Globals
    http = new HTTPBuilder()
    baseHeader = [
            Accept                     : 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
            'Accept-Encoding'          : 'gzip, deflate, sdch',
            'Accept-Language'          : 'en-US,en;q=0.8,ko;q=0.6',
            'Cache-Control'            : 'no-cache',
            Connection                 : 'keep-alive',
            Pragma                     : 'no-cache',
            'Upgrade-Insecure-Requests': '1',
            'User-Agent'               : 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36'
    ]

    login(config, cookies)
    doWakeUp(config, cookies)
    logout(config, cookies)
}
