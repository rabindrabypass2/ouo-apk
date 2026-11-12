package com.rabindra.ouo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button btnAirplane;
    private Button btnReload;
    private TextView tvStatus;
    private TextView tvIp;
    private ProgressBar progressBar;
    private PowerManager.WakeLock wakeLock;

    private static final String TARGET_URL = "https://ouo.io/WfnHg5";

    // Curated Genuine Modern Android Chrome Mobile User-Agents
    private static final String[] MOBILE_USER_AGENTS = new String[]{
        "Mozilla/5.0 (Linux; Android 14; Pixel 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.102 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; SM-S928B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.102 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; CPH2581) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.86 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; 23090RA98G) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.102 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; SM-A556B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.86 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; V2319) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.102 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; RMX3840) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.86 Mobile Safari/537.36",
        "Mozilla/5.0 (Linux; Android 14; motorola edge 50 pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.102 Mobile Safari/537.36"
    };

    private static final Random RANDOM = new Random();

    // Clean Direct Automation Script for ouo.io
    private static final String SCRIPT_OUO = 
        "(function(){\n" +
        "    if (window.__ouo_active) return;\n" +
        "    window.__ouo_active = true;\n" +
        "\n" +
        "    var O = location.origin,\n" +
        "    q = function(s, r) { return (r || document).querySelector(s); },\n" +
        "    D = function(id, r) { return (r || document).getElementById(id); },\n" +
        "    V = function(n, r) { var e = q('input[name=\"' + n + '\"]', r); return e ? e.value : ''; },\n" +
        "    E = function() {\n" +
        "        var b = D('btn-main');\n" +
        "        if (b) {\n" +
        "            b.className = 'btn btn-main';\n" +
        "            b.disabled = false;\n" +
        "            b.removeAttribute('disabled');\n" +
        "        }\n" +
        "        var t = D('timer');\n" +
        "        if (t) t.textContent = '0';\n" +
        "        var c = D('countdown');\n" +
        "        if (c) c.className = 'countdown end';\n" +
        "    },\n" +
        "    S = function() {\n" +
        "        return new Promise(function(res) {\n" +
        "            var tries = 0, ex = 0,\n" +
        "            g = function() {\n" +
        "                var e = D('x-token'), v = e && e.value;\n" +
        "                if (!v) {\n" +
        "                    var w = q('input[name=\"cf-turnstile-response\"]');\n" +
        "                    v = w && w.value;\n" +
        "                }\n" +
        "                return v || '';\n" +
        "            },\n" +
        "            v0 = g();\n" +
        "            if (v0) return res(v0);\n" +
        "            var iv = setInterval(function() {\n" +
        "                var v = g();\n" +
        "                if (v) {\n" +
        "                    clearInterval(iv);\n" +
        "                    return res(v);\n" +
        "                }\n" +
        "                if (ex < 3 && window.turnstile) {\n" +
        "                    try {\n" +
        "                        var w = q('.cf-turnstile');\n" +
        "                        turnstile.execute(w || undefined);\n" +
        "                        ex = 3;\n" +
        "                    } catch(e) { ex++; }\n" +
        "                }\n" +
        "                if (++tries > 60) {\n" +
        "                    clearInterval(iv);\n" +
        "                    res('');\n" +
        "                }\n" +
        "            }, 250);\n" +
        "        });\n" +
        "    },\n" +
        "    P = function(u, d) {\n" +
        "        return fetch(u, {\n" +
        "            method: 'POST',\n" +
        "            credentials: 'include',\n" +
        "            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },\n" +
        "            body: new URLSearchParams(d)\n" +
        "        }).then(function(r) { return r.text(); });\n" +
        "    },\n" +
        "    A = function(u) {\n" +
        "        if (!u) return location.href;\n" +
        "        return u.charAt(0) === '/' ? O + u : u;\n" +
        "    },\n" +
        "    C = function() {\n" +
        "        return location.pathname.split('/').filter(Boolean).pop();\n" +
        "    },\n" +
        "    N = function(u, d) {\n" +
        "        var f = document.createElement('form');\n" +
        "        f.method = 'POST';\n" +
        "        f.action = A(u);\n" +
        "        f.style.display = 'none';\n" +
        "        for (var k in d) {\n" +
        "            var i = document.createElement('input');\n" +
        "            i.type = 'hidden';\n" +
        "            i.name = k;\n" +
        "            i.value = d[k];\n" +
        "            f.appendChild(i);\n" +
        "        }\n" +
        "        document.body.appendChild(f);\n" +
        "        f.submit();\n" +
        "    };\n" +
        "\n" +
        "    function notifyStatus(msg) {\n" +
        "        try {\n" +
        "            if (window.AndroidBridge && typeof window.AndroidBridge.onStatusUpdate === 'function') {\n" +
        "                window.AndroidBridge.onStatusUpdate(msg);\n" +
        "            }\n" +
        "        } catch(e) {}\n" +
        "    }\n" +
        "\n" +
        "    // Single-window popup interceptor: ignore rogue ad popups\n" +
        "    var fakeTab = {\n" +
        "        location: {\n" +
        "            _href: 'about:blank',\n" +
        "            set href(val) {\n" +
        "                this._href = val;\n" +
        "                if (val && val !== 'about:blank') {\n" +
        "                    if (val.indexOf('ouo.io') !== -1 || val.indexOf('ouo.press') !== -1) {\n" +
        "                        window.location.href = val;\n" +
        "                    }\n" +
        "                }\n" +
        "            },\n" +
        "            get href() { return this._href; }\n" +
        "        },\n" +
        "        closed: false,\n" +
        "        close: function() { this.closed = true; }\n" +
        "    };\n" +
        "\n" +
        "    try {\n" +
        "        var _origReplace = window.location.replace.bind(window.location);\n" +
        "        window.location.replace = function(u) {\n" +
        "            if (u && (u.indexOf('omg10.com') !== -1 || u.indexOf('popads') !== -1 || u.indexOf('adsterra') !== -1)) return;\n" +
        "            _origReplace(u);\n" +
        "        };\n" +
        "\n" +
        "        window.open = function(u) {\n" +
        "            if (u && u !== 'about:blank' && !u.startsWith('javascript:')) {\n" +
        "                if (u.indexOf('ouo.io') !== -1 || u.indexOf('ouo.press') !== -1) {\n" +
        "                    window.location.href = u;\n" +
        "                }\n" +
        "            }\n" +
        "            return fakeTab;\n" +
        "        };\n" +
        "    } catch(e){}\n" +
        "\n" +
        "    try {\n" +
        "        window.blurred = false;\n" +
        "        window.onblur = null;\n" +
        "        setInterval(function() { window.blurred = false; }, 300);\n" +
        "    } catch(e){}\n" +
        "\n" +
        "    // Check destination\n" +
        "    function checkDestination() {\n" +
        "        var h = (location.href || '').toLowerCase();\n" +
        "        if (h && h !== 'about:blank' && h.indexOf('ouo.io') === -1 && h.indexOf('ouo.press') === -1 && h.indexOf('challenges.cloudflare') === -1 && h.indexOf('cloudflare.com') === -1) {\n" +
        "            if (window.AndroidBridge && typeof window.AndroidBridge.onDestinationDetected === 'function') {\n" +
        "                window.AndroidBridge.onDestinationDetected(location.href);\n" +
        "                return true;\n" +
        "            }\n" +
        "        }\n" +
        "        return false;\n" +
        "    }\n" +
        "\n" +
        "    if (checkDestination()) return;\n" +
        "\n" +
        "    E();\n" +
        "\n" +
        "    var f2 = D('form-go');\n" +
        "    if (f2) {\n" +
        "        notifyStatus('OUO Step 2: Submitting form-go...');\n" +
        "        N(f2.getAttribute('action'), { '_token': V('_token', f2), 'x-token': V('x-token', f2) });\n" +
        "        return;\n" +
        "    }\n" +
        "\n" +
        "    var f1 = D('form-captcha');\n" +
        "    if (!f1) {\n" +
        "        console.log('ouo skip: no gate on this page');\n" +
        "        return;\n" +
        "    }\n" +
        "\n" +
        "    notifyStatus('OUO Step 1: Solving Turnstile...');\n" +
        "\n" +
        "    var run = function() {\n" +
        "        return S().then(function(x) {\n" +
        "            notifyStatus('Turnstile Token Ready! Submitting Step 1...');\n" +
        "            var d = { '_token': V('_token', f1), 'x-token': x, 'v-token': V('v-token', f1) };\n" +
        "            if (x) d['cf-turnstile-response'] = x;\n" +
        "            return P(A(f1.getAttribute('action')), d);\n" +
        "        }).then(function(h) {\n" +
        "            notifyStatus('Step 1 Done -> Parsing Step 2...');\n" +
        "            var p = new DOMParser().parseFromString(h, 'text/html'),\n" +
        "            g = D('form-go', p) || p.querySelector('form[action*=\"xreallcygo\"]'),\n" +
        "            t = V('_token', p);\n" +
        "            if (!g || !t) throw new Error('step2 missing');\n" +
        "            try { history.replaceState(null, '', '/go/' + C()); } catch(e) {}\n" +
        "            notifyStatus('Submitting Step 2...');\n" +
        "            N(g.getAttribute('action'), { '_token': t, 'x-token': V('x-token', p) });\n" +
        "        });\n" +
        "    };\n" +
        "\n" +
        "    run().catch(function(err) {\n" +
        "        notifyStatus('Retrying Turnstile...');\n" +
        "        try { turnstile.reset(); } catch(e) {}\n" +
        "        return run();\n" +
        "    }).catch(function(err) {\n" +
        "        notifyStatus('Fallback route /fbc/ ...');\n" +
        "        location.href = O + '/fbc/' + C();\n" +
        "    });\n" +
        "})();";

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean isRotating = false;
    private boolean isDestinationTriggering = false;
    private boolean isAutoReloading = false;
    private boolean isResetting = false;
    private boolean isHandlingError = false;
    private String currentIp = "...";

    // 75-Second Watchdog Timer: If shortener stalls anywhere, reset and start fresh with a new IP
    private final Runnable watchdogRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isRotating && !isDestinationTriggering && !isResetting) {
                mainHandler.post(() -> {
                    tvStatus.setTextColor(Color.parseColor("#FFA500"));
                    tvStatus.setText("Watchdog: timeout! Rotating IP...");
                    triggerRogueRedirectReset("timeout_75s");
                });
            }
        }
    };

    private void armWatchdog() {
        mainHandler.removeCallbacks(watchdogRunnable);
        mainHandler.postDelayed(watchdogRunnable, 75000);
    }

    private void disarmWatchdog() {
        mainHandler.removeCallbacks(watchdogRunnable);
    }

    public class WebAppInterface {
        @JavascriptInterface
        public void onStatusUpdate(String status) {
            runOnUiThread(() -> {
                if (!isRotating && !isDestinationTriggering && !isResetting && status != null) {
                    tvStatus.setTextColor(Color.parseColor("#00E676"));
                    tvStatus.setText(status);
                    armWatchdog();
                }
            });
        }

        @JavascriptInterface
        public void onDestinationDetected(String destUrl) {
            runOnUiThread(() -> checkAndHandleDestination(destUrl));
        }

        @JavascriptInterface
        public void onSearchGateDetected() {
            runOnUiThread(MainActivity.this::triggerSearchGateReload);
        }

        @JavascriptInterface
        public void onRogueRedirectDetected(String rogueUrl) {
            // Silently ignore rogue ad attempts
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAirplane = findViewById(R.id.btn_airplane);
        btnReload = findViewById(R.id.btn_reload);
        tvStatus = findViewById(R.id.tv_status);
        tvIp = findViewById(R.id.tv_ip);
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.webview);

        webView.setBackgroundColor(Color.WHITE);

        // Keep CPU awake when screen is locked
        try {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ouo:WakeLock");
                wakeLock.acquire();
            }
        } catch (Exception ignored) {}

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportMultipleWindows(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Suppress X-Requested-With header to match standalone Chrome for Cloudflare Turnstile
        if (WebViewFeature.isFeatureSupported(WebViewFeature.REQUESTED_WITH_HEADER_ALLOW_LIST)) {
            WebSettingsCompat.setRequestedWithHeaderOriginAllowList(settings, Collections.emptySet());
        }

        String initialUa = MOBILE_USER_AGENTS[RANDOM.nextInt(MOBILE_USER_AGENTS.length)];
        settings.setUserAgentString(initialUa);

        webView.addJavascriptInterface(new WebAppInterface(), "AndroidBridge");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                result.confirm();
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUrl(view, request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrl(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url != null && !url.equals("about:blank")) {
                    if (isDestinationUrl(url)) {
                        tvStatus.setTextColor(Color.parseColor("#00E676"));
                        tvStatus.setText("Opening Destination...");
                        return;
                    }
                    String lower = url.toLowerCase();
                    if (lower.contains("google.com/search") || lower.contains("google.co.in/search") || lower.contains("koekke.net")) {
                        triggerSearchGateReload();
                        return;
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                if (url == null || url.equals("about:blank")) return;

                if (checkAndHandleDestination(url)) return;

                if (isKnownRogueAdUrl(url)) {
                    return;
                }

                tvStatus.setTextColor(Color.parseColor("#00E676"));
                tvStatus.setText("Running");
                view.evaluateJavascript(SCRIPT_OUO, null);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request != null && request.isForMainFrame()) {
                    handleConnectionError();
                }
            }
        });

        btnAirplane.setOnClickListener(v -> startAirplaneReset());
        btnReload.setOnClickListener(v -> refreshIpAndLoad(true));

        checkRootAccess();

        // Load target URL immediately on launch!
        tvStatus.setTextColor(Color.parseColor("#00E676"));
        tvStatus.setText("Loading...");
        refreshIpAndLoad(true);
    }

    private boolean isDestinationUrl(String url) {
        if (url == null || url.isEmpty() || url.equals("about:blank")) return false;
        String lower = url.toLowerCase();
        if (lower.contains("ouo.io") || lower.contains("ouo.press")) return false;
        if (lower.contains("challenges.cloudflare.com") || lower.contains("cloudflare.com")) return false;
        if (isKnownRogueAdUrl(url)) return false;
        if (lower.contains("google.com/search") || lower.contains("google.co.in/search")) return false;
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private boolean isKnownRogueAdUrl(String url) {
        if (url == null) return false;
        String lower = url.toLowerCase();
        return lower.contains("omg10.com") || lower.contains("popads") || lower.contains("adsterra") || lower.contains("monetag");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.resumeTimers();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webView != null) {
            webView.resumeTimers();
        }
    }

    @Override
    protected void onDestroy() {
        disarmWatchdog();
        try {
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }
        } catch (Exception ignored) {}
        super.onDestroy();
    }

    private void handleConnectionError() {
        if (isHandlingError || isRotating) return;
        isHandlingError = true;

        mainHandler.post(() -> {
            tvStatus.setTextColor(Color.parseColor("#FFA500"));
            tvStatus.setText("Waiting for 4G Data...");
        });

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try { Thread.sleep(700); } catch (InterruptedException ignored) {}
                if (isInternetAvailable()) {
                    break;
                }
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            mainHandler.post(() -> {
                isHandlingError = false;
                if (!isRotating && webView != null) {
                    tvStatus.setTextColor(Color.parseColor("#00E676"));
                    tvStatus.setText("Reloading...");
                    webView.loadUrl(TARGET_URL);
                }
            });
        }).start();
    }

    private boolean handleUrl(WebView view, String url) {
        if (url == null) return false;
        if (isDestinationUrl(url)) {
            checkAndHandleDestination(url);
            return false;
        }
        String lower = url.toLowerCase();
        if (lower.contains("google.com/search") || lower.contains("google.co.in/search") || lower.contains("koekke.net")) {
            triggerSearchGateReload();
            return true;
        }
        if (isKnownRogueAdUrl(url)) {
            return true;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return false;
        }
        return true;
    }

    // Search Gate Auto-Refresh
    private synchronized void triggerSearchGateReload() {
        if (isRotating || isAutoReloading || isResetting || isDestinationTriggering) return;
        isAutoReloading = true;

        mainHandler.post(() -> {
            tvStatus.setTextColor(Color.parseColor("#FFA500"));
            tvStatus.setText("Search Gate! Auto Refreshing...");
            Toast.makeText(MainActivity.this, "Search Gate Detected! Auto Refreshing...", Toast.LENGTH_SHORT).show();

            try {
                if (btnReload != null) {
                    btnReload.performClick();
                } else {
                    refreshIpAndLoad(true);
                }
            } catch (Exception e) {
                refreshIpAndLoad(true);
            }

            mainHandler.postDelayed(() -> {
                isAutoReloading = false;
            }, 3000);
        });
    }

    // Rogue Redirect Reset
    private synchronized void triggerRogueRedirectReset(String rogueUrl) {
        if (isRotating || isDestinationTriggering || isResetting) return;
        isResetting = true;
        disarmWatchdog();

        mainHandler.post(() -> {
            try {
                if (webView != null) {
                    webView.stopLoading();
                    webView.loadUrl("about:blank");
                }
            } catch (Exception ignored) {}

            wipeSession();

            tvStatus.setTextColor(Color.parseColor("#FF5252"));
            tvStatus.setText("Resetting & Rotating IP...");
            Toast.makeText(MainActivity.this, "Resetting & Rotating IP...", Toast.LENGTH_SHORT).show();

            mainHandler.postDelayed(() -> {
                isResetting = false;
                startAirplaneReset();
            }, 1500);
        });
    }

    // Destination Handler: lets destination open, dwell 5s, then triggers fresh IP cycle
    private synchronized boolean checkAndHandleDestination(String url) {
        if (!isDestinationUrl(url)) return false;
        if (isRotating || isDestinationTriggering || isResetting) {
            return true;
        }
        isDestinationTriggering = true;
        disarmWatchdog();

        mainHandler.post(() -> {
            tvStatus.setTextColor(Color.parseColor("#00E676"));
            tvStatus.setText("Destination Reached! (Dwell 5s)");
            Toast.makeText(MainActivity.this, "✅ Destination Reached! Dwell 5s...", Toast.LENGTH_SHORT).show();

            mainHandler.postDelayed(() -> {
                isDestinationTriggering = false;
                wipeSession();
                startAirplaneReset();
            }, 5000);
        });
        return true;
    }

    private void checkRootAccess() {
        new Thread(() -> {
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "echo root_ok"});
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = r.readLine();
                p.waitFor();
                if ("root_ok".equals(line)) {
                    runRootCommand("dumpsys deviceidle whitelist +" + getPackageName());
                    mainHandler.post(() -> {
                        tvStatus.setTextColor(Color.parseColor("#00E676"));
                        tvStatus.setText("Root Ready");
                    });
                }
            } catch (Exception ignored) {}
        }).start();
    }

    private boolean isInternetAvailable() {
        String[] testUrls = new String[]{
            "http://connectivitycheck.gstatic.com/generate_204",
            "http://clients3.google.com/generate_204",
            "https://api.ipify.org"
        };
        for (String testUrl : testUrls) {
            try {
                URL url = new URL(testUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(1500);
                conn.setReadTimeout(1500);
                conn.setInstanceFollowRedirects(false);
                int code = conn.getResponseCode();
                if (code == 200 || code == 204) {
                    return true;
                }
            } catch (Exception ignored) {}
        }
        return false;
    }

    // Airplane Reset: Airplane ON -> Randomize Android ID -> Airplane OFF -> Poll 4G -> Wipe -> Reload
    private void startAirplaneReset() {
        if (isRotating) return;
        isRotating = true;
        isDestinationTriggering = false;
        isAutoReloading = false;
        isResetting = false;
        isHandlingError = false;
        setButtonsEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setTextColor(Color.parseColor("#FFA500"));
        tvStatus.setText("Airplane ON...");

        new Thread(() -> {
            // 1. Airplane ON
            runRootCommand("cmd connectivity airplane-mode enable || settings put global airplane_mode_on 1");

            // Randomize Android ID
            try {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 16; i++) {
                    sb.append(Integer.toHexString(RANDOM.nextInt(16)));
                }
                runRootCommand("settings put secure android_id " + sb.toString());
            } catch (Exception ignored) {}

            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

            mainHandler.post(() -> tvStatus.setText("Airplane OFF..."));

            // 2. Airplane OFF
            runRootCommand("cmd connectivity airplane-mode disable || settings put global airplane_mode_on 0");

            mainHandler.post(() -> tvStatus.setText("Connecting 4G..."));

            // Active poll for 4G data
            boolean connected = false;
            for (int i = 0; i < 45; i++) {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                if (isInternetAvailable()) {
                    connected = true;
                    break;
                }
            }
            if (!connected) {
                mainHandler.post(() -> tvStatus.setText("Awaiting 4G..."));
                try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
            } else {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }

            // 3. Wipe and reload target
            mainHandler.post(() -> {
                Toast.makeText(MainActivity.this, "New IP & ID Active!", Toast.LENGTH_SHORT).show();
                isRotating = false;
                setButtonsEnabled(true);
                refreshIpAndLoad(true);
            });
        }).start();
    }

    private void setButtonsEnabled(boolean enabled) {
        btnAirplane.setEnabled(enabled);
        btnReload.setEnabled(enabled);
    }

    private boolean runRootCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            int exit = p.waitFor();
            return (exit == 0);
        } catch (Exception ignored) {
            return false;
        }
    }

    private void wipeSession() {
        try {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
            WebStorage.getInstance().deleteAllData();
            if (webView != null) {
                webView.clearCache(true);
                webView.clearHistory();
                webView.clearFormData();
            }
        } catch (Exception ignored) {}
    }

    private void refreshIpAndLoad(boolean reloadWeb) {
        wipeSession();
        armWatchdog();

        // Rotate User-Agent
        try {
            String nextUa = MOBILE_USER_AGENTS[RANDOM.nextInt(MOBILE_USER_AGENTS.length)];
            if (webView != null) {
                webView.getSettings().setUserAgentString(nextUa);
            }
        } catch (Exception ignored) {}

        if (reloadWeb) {
            progressBar.setVisibility(View.VISIBLE);
            tvStatus.setTextColor(Color.parseColor("#00E676"));
            tvStatus.setText("Loading...");
            webView.loadUrl(TARGET_URL);
        }

        // Fetch current public IP
        new Thread(() -> {
            String ip = fetchPublicIp();
            mainHandler.post(() -> {
                currentIp = ip;
                tvIp.setText("IP: " + currentIp);
            });
        }).start();
    }

    private String fetchPublicIp() {
        String[] endpoints = {
                "https://api.ipify.org",
                "https://icanhazip.com",
                "https://checkip.amazonaws.com"
        };
        for (String ep : endpoints) {
            try {
                URL url = new URL(ep);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(2500);
                conn.setReadTimeout(2500);
                if (conn.getResponseCode() == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String ip = r.readLine();
                    r.close();
                    if (ip != null && !ip.trim().isEmpty()) {
                        return ip.trim();
                    }
                }
            } catch (Exception ignored) {}
        }
        return "Connected";
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            moveTaskToBack(true);
        }
    }
}

