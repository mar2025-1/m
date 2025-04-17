@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "videos.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVideoDao(db: VideoDatabase): VideoDao = db.videoDao()
}