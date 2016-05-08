#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_ironfactory_allinoneenglish_controllers_activities_TabActivity_getMsgFromJni(JNIEnv *env,
                                                                                      jobject instance) {

    // TODO

//    return (*env)->NewStringUTF(env, returnValue);
    return (*env)->NewStringUTF(env, "HIHI");
}