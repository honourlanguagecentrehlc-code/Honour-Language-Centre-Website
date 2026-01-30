// keep-alive.js
(function() {
    'use strict';
    
    console.log('Keep-alive script loaded at', new Date().toISOString());
    
    // Ping server every 5 minutes to keep Render instance alive
    setInterval(() => {
        fetch(window.location.href, {
            method: 'HEAD', // Lightweight HEAD request
            cache: 'no-store'
        }).then(() => {
            console.log('Keep-alive ping successful at', new Date().toLocaleTimeString());
        }).catch(() => {
            console.log('Keep-alive ping failed');
        });
    }, 300000); // 5 minutes = 300000 milliseconds
    
    // Also ping when user returns to the tab
    document.addEventListener('visibilitychange', () => {
        if (!document.hidden) {
            console.log('Page became visible - refreshing connection');
            fetch(window.location.href, { method: 'HEAD', cache: 'no-store' })
                .catch(() => {});
        }
    });
    
    // Initial ping
    setTimeout(() => {
        fetch(window.location.href, { method: 'HEAD', cache: 'no-store' })
            .catch(() => {});
    }, 10000); // First ping 10 seconds after page load
    
})();