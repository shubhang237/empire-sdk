require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-empire-sdk"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "10.0" }
  s.source       = { :git => "https://repo.tech.gameskraft.com/projects/TEN/repos/empire-sdk.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,mm}"

  s.dependency "React-Core"
end
