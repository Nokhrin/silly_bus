package org.example.inheritance;

/**
 * ограничение расширения типа 
 */
sealed interface INotification permits EmailJavaSealed, IEmailNotification {
    public String send();
}
