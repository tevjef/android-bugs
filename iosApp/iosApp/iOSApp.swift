import SwiftUI
import Shared

@main
struct iOSApp: App {

    init() {
        AppGraph_iosKt.doInitIosGraph()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

