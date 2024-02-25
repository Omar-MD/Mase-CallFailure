function fn() {
    const env = karate.env; // get system property 'karate.env'
    karate.log('karate.env system property was:', env);

    const config = {
        baseUrl: 'http://localhost:' + karate.properties['local.server.port'],
    };

    karate.log('baseUrl ' + config.baseUrl)
    return config;
}
