@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("mard_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDownloadManager(app: Application): DownloadManager {
        return app.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
}