const prod = process.env.NODE_ENV === 'production'

module.exports = {
  site: {
    url: prod ? 'your production url' : 'http://localhost:9001'
  },
  user: {
    name: 'YoshinoriN'
  }
}
