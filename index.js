const handler = require('./cljs/main.js').simpleui.app.handler;
import { Router, createCors, error, json } from 'itty-router'

const { preflight, corsify } = createCors({
	origins: ['*'],
	methods: ['GET', 'POST', 'PATCH', 'DELETE', 'PUT'],
})

const router = Router()

router
	// embed preflight upstream to handle all OPTIONS requests
	.all('*', preflight)

	.all('*', handler)

export default {
	fetch: (...args) => router
		.handle(...args)
		.then(json)

		// embed corsify downstream to attach CORS headers
		.then(corsify)
		.catch(error)
}
