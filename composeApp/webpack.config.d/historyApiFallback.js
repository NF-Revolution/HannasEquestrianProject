/**
 * Enable Single Page Application (SPA) routing
 * 
 * This configuration ensures that all routes serve index.html,
 * allowing client-side routing to work properly with direct URL access.
 * 
 * Without this, accessing routes like /horses or /stables directly
 * would result in a 404 error.
 */

// Enable for development server
if (config.devServer) {
    config.devServer.historyApiFallback = true
}

// For production builds served with a web server, you'll need to configure
// the web server (nginx, Apache, etc.) to serve index.html for all routes.
// Example nginx configuration:
//
// location / {
//     try_files $uri $uri/ /index.html;
// }
