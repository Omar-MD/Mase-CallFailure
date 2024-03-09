function fn() {
    const env = karate.env;
    karate.log('karate.env system property was:', env);
    
    const config = {
        baseUrl: 'http://localhost:' + karate.properties['local.server.port'],
        fileUtil: Java.type('com.tus.cipher.services.FileUtil')
    };
    
    return config;
}
