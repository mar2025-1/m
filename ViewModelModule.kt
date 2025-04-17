@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MARDViewModelFactory): ViewModelProvider.Factory
}