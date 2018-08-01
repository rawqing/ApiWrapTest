package yq.test.handler.constant

val sentenceRegex = "\\\$\\{.+?}".toRegex()
val varRegex = Regex(pattern = "\\\$[a-z]([a-z]|[A-Z]|[0-9]|_)+")

const val defaultYml = "dubhe1.yml"
const val caseExtension = "yml"