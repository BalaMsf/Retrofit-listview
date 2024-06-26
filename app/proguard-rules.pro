# Keep the MainActivity class and its methods
-keep class com.example.recyclerview.MainActivity {
    <init>();
    # Add other methods you want to keep
}
# Keep the PostAdapter class and its methods
-keep class com.example.recyclerview.adapter.PostAdapter {
    <init>();
    # Add other methods you want to keep
}
-keep class com.example.recyclerview.database.AppDatabase {
    <init>();
    # Add other methods you want to keep
}
-keep class com.example.recyclerview.database.PostDao {
    <init>();
    # Add other methods you want to keep
}
-keep class com.example.recyclerview.model.Post {
    <init>();
    # Add other methods you want to keep
}
-keep class com.example.recyclerview.retrofit.Api {
    <init>();
    # Add other methods you want to keep
}
-keep class com.example.recyclerview.retrofit.RetrofitClient {
    <init>();
    # Add other methods you want to keep
}
# Keep all public classes, interfaces, and enums
-keep public class * {
    public protected *;
}

# Keep all native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Prevent obfuscation of any inner classes
-keepattributes InnerClasses

# Keep line number information for debugging
-keepattributes SourceFile,LineNumberTable

# If you have annotation processors, use this rule instead:
#-keep class ** {
#    @javax.annotation.processing.Processor *;
#}
