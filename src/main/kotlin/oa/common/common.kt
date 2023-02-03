package oa.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

// Return a lazy logger delegate property
fun <T : Any> T.logger(): Lazy<Logger> {
    // 使logger的名字始终和最外层的类一致，即使是在companion object中初始化属性
    val ofClass = this.javaClass
    val clazz = ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass

    return lazy { LoggerFactory.getLogger(clazz.name) }
}
