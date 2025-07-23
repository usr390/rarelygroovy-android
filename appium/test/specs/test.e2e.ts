import { remote } from 'webdriverio'

describe('Rarelygroovy', () => {
  it('should show Upcoming Events on launch', async () => {
    const el = await $('android=new UiSelector().text("Upcoming Events")')
    expect(await el.isDisplayed()).toBe(true)

    await driver.execute('mobile: terminateApp', { appId: 'com.example.rarelygroovy' })
  })
})


