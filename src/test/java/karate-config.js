function fn() {
    const env = karate.env;
    karate.log('karate.env system property was:', env);

    const config = {
        baseUrl: 'http://localhost:' + karate.properties['local.server.port'],
        fileUtil: Java.type('com.tus.cipher.services.FileUtil'),
        headers: {
            'Content-Type': 'application/json'
        },
        maxImportTime: 120000,
        maxQueryTime: 2000
    };

    // Function to get the authentication token for a given user role
    config.getAuthToken = function(username, password, role) {
        let response = karate.callSingle('classpath:com/tus/cipher/karate/auth.feature?' + role, {
            'baseUrl': config.baseUrl,
            'username': username,
            'password': password
        });
        return response.authToken;
    };

    // Function to import base data
    config.importBaseData = function(adminToken) {
        karate.callSingle('classpath:com/tus/cipher/karate/import.feature', {
            'baseUrl': config.baseUrl,
            'adminToken': adminToken,
            'maxImportTime': config.maxImportTime
        });
    };

    // Import base data using admin token
    config.importBaseData(config.getAuthToken('admin', 'password', 'ADMIN'));
    
    return config;
}
