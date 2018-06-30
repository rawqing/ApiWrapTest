package yq.test.handler.constant

val sentenceRegex = "\\\$\\{.+?}".toRegex()
val varRegex = Regex(pattern = "\\\$[a-z]([a-z]|[A-Z]|[0-9]|_)+")