## SimpleUI Cloudflare Demo

### Development

For now we need advanced optimizations to clean out js that Cloudflare doesn't like.

```bash
npm i
clj -M:watch
npm run dev
```

### Deploy

```bash
clj -M:release
clj -M:snippets
npm run deploy
```
