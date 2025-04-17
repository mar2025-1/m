@Module
@InstallIn(ActivityComponent::class)
interface Bindings {

    @Binds
    fun bindMainActivity(activity: MainActivity): Activity

    @Binds
    fun bindVideoRepository(repo: VideoRepositoryImpl): VideoRepository
}